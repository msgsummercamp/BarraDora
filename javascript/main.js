const button = document.getElementById('dogButton');
const image = document.getElementById('dogImage');
const loading = document.getElementById('loading');

button.addEventListener('click', async () => {
    loading.style.display = 'block';
    image.style.display = 'none';

    try {
        const response = await fetch('https://dog.ceo/api/breeds/image/random');
        if (!response.ok) {
            throw new Error('Network error');
        }

        const data = await response.json();
        image.src = data.message;
        image.style.display = 'block';
    } catch (error) {
        alert('Failed to load dog image.');
        console.error(error);
    } finally {
        loading.style.display = 'none';
    }
});
