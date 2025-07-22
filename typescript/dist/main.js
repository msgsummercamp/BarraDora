"use strict";
var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
// Get DOM elements and type them
const button = document.getElementById('dogButton');
const image = document.getElementById('dogImage');
const loading = document.getElementById('loading');
button.addEventListener('click', () => __awaiter(void 0, void 0, void 0, function* () {
    loading.style.display = 'block';
    image.style.display = 'none';
    try {
        const response = yield fetch('https://dog.ceo/api/breeds/image/random');
        if (!response.ok) {
            throw new Error('Failed to fetch');
        }
        const data = yield response.json();
        image.src = data.message;
        image.style.display = 'block';
    }
    catch (error) {
        console.error('Error:', error);
        alert('Could not load dog image.');
    }
    finally {
        loading.style.display = 'none';
    }
}));
