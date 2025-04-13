setTimeout(() => {
    const alertElement = document.querySelector('.alert');
    if (alertElement) {
        alertElement.classList.remove('show');
        alertElement.classList.add('fade');
        setTimeout(() => alertElement.remove(), 500);
    }
}, 5000); 