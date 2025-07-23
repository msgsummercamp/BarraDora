const button = document.getElementById('dogButton');
const image = document.getElementById('dogImage');
const loading = document.getElementById('loading');
const errorMessage = document.getElementById('errorMessage');

button.onclick = async () => {
    loading.style.display = 'block';
    image.style.display = 'none';
    errorMessage.style.display = 'none';

    try {
        const response = await fetch('https://dog.ceo/api/breeds/image/random');
        if (!response.ok) {
            throw new Error('Network error');
        }

        const data = await response.json();
        image.src = data.message;
        image.style.display = 'block';
    } catch (error) {
        errorMessage.textContent = 'Failed to load dog image.';
        errorMessage.style.display = 'block';
        console.error(error);
    } finally {
        loading.style.display = 'none';
    }
};