//header files
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
#include <time.h>
#include <unistd.h>
#include <sys/wait.h>
#include <signal.h>

//definitions
#define MAX_SIZE 200
#define MAX_WORDS 50
#define MAX_HISTORY 100
#define MAX_COMMANDS 10

//struct to store commands info
struct CommandInfo {
    pid_t pid;
    char command[MAX_SIZE + 1]; //+1 to accomodate \n or \0
    time_t start_time;
    time_t end_time;
    double execution_time;
};

//global variables
int history_count = 0;
struct CommandInfo history[MAX_HISTORY];

//function declarations
static void my_handler(int signum);
void termination_report();
void shell_loop();
char* read_user_input();
int launch(char* command);
int create_process_and_run(char* command);
int create_child_process(char *command, int input_fd, int output_fd);

int main(){
    //signal part to handle ctrl c (from lecture 7)
    struct sigaction sig;
    if (memset(&sig, 0, sizeof(sig)) == 0){
        perror("memset");
        exit(1);
    }
    sig.sa_handler = my_handler;
    if (sigaction(SIGINT, &sig, NULL) == -1){
        perror("sigaction");
        exit(1);
    }

    printf("Initializing simple shell...\n");
    shell_loop();
    printf("Exiting simple shell...\n");
    termination_report();
    return 0;
}

//signal handler
static void my_handler(int signum) {
    if(signum == SIGINT) {
        printf("\nCaught SIGINT signal for termination\n");
        printf("Exiting simple shell...\n");
        termination_report();
        exit(0);
    }
}

//the function called upon termination to print command details
//in here we are formatting time and printing iterating over the global array
void termination_report(){
    if (history_count>0){
        printf("\nCommand PID  Start_time  End_time  Execution_time (PID is -1 if a command was not executed through process creation)\n");
        struct tm *st, *et;
        char sta[9], eta[9];
        for (int i=0; i<history_count; i++){
            st = localtime(&history[i].start_time);
            et = localtime(&history[i].end_time);
            if (strftime(sta, 9, "%H:%M:%S", st) == 0){
                perror("strftime");
            }
            if (strftime(eta, 9, "%H:%M:%S", et) == 0){
                perror("strftime");
            }
            printf("%s %d %s %s %.2f\n",history[i].command,history[i].pid,sta,eta,history[i].execution_time);
        }
    }
}

//infinite loop for the shell
//we take user input, pass it over to launch and wait for execution and update time fields
void shell_loop(){
    int status;
    do{
        //this prints the output in magenta colour
        printf("\033[1;35mos@shell:~$\033[0m ");
        char* command = read_user_input();
        time(&history[history_count].start_time);
        history[history_count].pid = -1;
        status = launch(command);
        time(&history[history_count].end_time);
        history[history_count].execution_time = difftime(history[history_count].end_time, history[history_count].start_time);
        history_count++;
    } while(status);
}

//here we take input and remove trailing \n and update global array
char* read_user_input(){
    static char input[MAX_SIZE+1];
    if (fgets(input,MAX_SIZE+1,stdin) == NULL){
        perror("fgets");
        exit(1);
    }
    int input_len = strlen(input);
    if (input_len>0 && input[input_len-1]=='\n'){
        input[input_len-1] = '\0';
    }
    strcpy(history[history_count].command,input);
    return input;
}

//here we execute custom commands which dont require process creation and their pids are -1
int launch(char* command){
    //removing leading whitespaces
    while(*command==' ' || *command=='\t'){
        command++;
    }
    //removing trailing whitespaces
    // char *end = command + strlen(command)-1;
    // while (end>=command && (*end==' ' || *end=='\t')){
    //     *end = '\0';
    //     end--;
    // }

    if (strcmp(command,"history") == 0){
        for (int i=0; i<history_count+1; i++){
            printf("%s\n",history[i].command);
        }
        return 1;
    }
    if (strcmp(command,"exit") == 0){
        return 0;
    }
    if (strcmp(command, "") == 0){
        history_count--;
        return 1;
    }
    int status;
    status = create_process_and_run(command);
    return status;
}

//here we check for the pipe and & in the commands and create child process after that in other function
int create_process_and_run(char* command){
    //separating pipe commands (|)
    int command_count = 0;
    char* commands[MAX_COMMANDS];
    char* token = strtok(command, "|");
    while (token != NULL){
        commands[command_count++] = token;
        token = strtok(NULL, "|");
    }
    if (command_count>MAX_COMMANDS){
        printf("you have used more than 9 pipes, try again");
        return 1;
    }

    //executing if pipe is present in command input except the last one 
    int i, prev_read = STDIN_FILENO;
    int pipes[2], child_pids[command_count];
    //we iterate and execute every command through process creation and keep updating read and write ends of pipe
    for (i=0; i < command_count-1; i++){
        if (pipe(pipes) == -1){
            perror("pipe");
            exit(1);
        }

        if ((child_pids[i]=create_child_process(commands[i], prev_read, pipes[1])) < 0){
            perror("create_child_process");
            exit(1);
        }

        if (close(pipes[1]) == -1){
            perror("close");
            exit(1);
        }
        prev_read = pipes[0];
    }

    //the last command whose output is to be displayed on STDOUT
    //checking if it a background process
    bool background_process = 0;
    if (commands[i][strlen(commands[i]) - 1] == '&') {
        // Remove the '&' symbol
        commands[i][strlen(commands[i]) - 1] = '\0';
        background_process = 1;
    }
    if ((child_pids[i]=create_child_process(commands[i], prev_read, STDOUT_FILENO)) < 0){
        perror("create_child_process");
        exit(1);
    }
    
    //updating global array for pids
    history[history_count].pid = child_pids[i];

    if (!background_process) {
        //wait for child process if command is not background
        for (i = 0; i < command_count; i++) {
            int ret;
            int pid = waitpid(child_pids[i], &ret, 0);
            if (pid < 0) {
                perror("waitpid");
                exit(1);
            }
            if (!WIFEXITED(ret)) {
                printf("Abnormal termination of %d\n", pid);
            }
        }
    }
    else{
        //print pid and command if it is being executed in background
        printf("%d %s\n", child_pids[command_count-1],command);
    }
}

int create_child_process(char *command, int input_fd, int output_fd){
    int status = fork();
    if (status < 0){
        printf("fork() failed.\n");
        exit(1);
    }
    else if (status == 0){
        //child process
        //updating/copying I/O descriptors
        if (input_fd != STDIN_FILENO)
        {
            if (dup2(input_fd, STDIN_FILENO) == -1){
                perror("dup2");
                exit(1);
            }
            if (close(input_fd) == -1){
                perror("close");
                exit(1);
            }
        }
        if (output_fd != STDOUT_FILENO)
        {
            if (dup2(output_fd, STDOUT_FILENO) == -1){
                perror("dup2");
                exit(1);
            }
            if (close(output_fd) == -1){
                perror("close");
                exit(1);
            }
        }

        //creating an array of indiviudal command and its arguments
        char* arguments[MAX_WORDS+1]; //+1 to accomodate NULL
        int argument_count = 0;
        char* token = strtok(command, " ");
        while (token != NULL){
            arguments[argument_count++] = token;
            token = strtok(NULL, " ");
        }
        arguments[argument_count] = NULL;

        //exec to execute command (actual part of child process)
        if (execvp(arguments[0],arguments) == -1) {
            perror("execvp");
            printf("Not a valid/supported command.\n");
            exit(1);
        }
        exit(0);
    }
    else{
        //parent process
        return status;
    }
}