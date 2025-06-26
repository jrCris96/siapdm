const menu= document.getElementById('menu');
const sidebar= document.getElementById('sidebar');
const main= document.getElementById('main');

const input = document.getElementById('archivoImagen');
const image = document.getElementById('imgFile');

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

// Escucha el cambio
input.addEventListener('change', () => {
    const file = input.files[0];
    if (file && file.type.startsWith('image/')) {
        // Revoca URL anterior si existe
        if (image.src) URL.revokeObjectURL(image.src);

        image.src = URL.createObjectURL(file);
        image.style.display = 'block';
    } else {
        alert('Selecciona una imagen válida.');
        image.style.display = 'none';
    }
});