const url = "https://api.dictionaryapi.dev/api/v2/entries/en/";
const result = document.getElementById("result");
const sound = document.getElementById("sound");
const btn = document.getElementById("search-btn");

btn.addEventListener("click", () => {
    let inpWord = document.getElementById("inp-word").value;
    fetch(`${url}${inpWord}`).then((response) => response.json()).then((data) => {
        console.log(data);
        result.innerHTML = `<div class="word">
        <h3>${inpWord}</h3>
        <button><i class="fas fa-volume-up"></i></button>
    </div>
    <div class="details">
        <p>pos</p>
        <p>/sample/</p>
    </div>
    <div>
        <p class="word-meaning">
            Lorem ipsum adipisicing elit. Autem quod, magni, ipsam harum tempora nemo dolorem maiores.
        </p>
        <p class="word-example">Lorem ipsum dolor sit, amet consectetur adipisicing elit. Quasi, ratione.</p>`;

    });
});

