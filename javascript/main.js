function fetchDogImage() {
    const loading = document.getElementById('loading');
    const image = document.getElementById('dogImage');
    const errorMessage = document.getElementById('errorMessage');

    loading.style.display = 'block';
    image.style.display = 'none';
    errorMessage.style.display = 'none';

    fetch('https://dog.ceo/api/breeds/image/random')
        .then((response) => {
            if (!response.ok) {
                throw new Error('Network error');
            }
            return response.json();
        })
        .then((data) => {
            image.src = data.message;
            image.style.display = 'block';
        })
        .catch((error) => {
            errorMessage.textContent = 'Failed to load dog image.';
            errorMessage.style.display = 'block';
            console.error(error);
        })
        .finally(() => {
            loading.style.display = 'none';
        });
}