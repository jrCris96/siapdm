const menu= document.getElementById('menu');
const sidebar= document.getElementById('sidebar');
const main= document.getElementById('main');
const image= document.querySelector("#imgFile");
const input= document.querySelector("#inputFile");

let listElements= document.querySelectorAll('.list__button--click');

menu.addEventListener('click',()=>{
    sidebar.classList.toggle('menu-toggle');
    menu.classList.toggle('menu-toggle');
    main.classList.toggle('menu-toggle');
});

listElements.forEach(listElement =>{
    listElement.addEventListener('click', ()=>{
        
        listElement.classList.toggle('arrow');

        let height= 0;
        let menu= listElement.nextElementSibling;
        if(menu.clientHeight == "0"){
            height=menu.scrollHeight;
        }

        menu.style.height= `${height}px`
    })
});

input.addEventListener("change", () => {
    const file = input.files[0];
    if (file && file.type.startsWith("image/")) {
        image.src = URL.createObjectURL(file);
        image.style.display = "block";  // Mostrar la imagen
    } else {
        alert("Selecciona una imagen válida.");
        image.style.display = "none";  // Ocultar la imagen si no es válida
    }
});