//const BASE_URL = 'http://localhost:8080';   //Local
const BASE_URL = 'https://news-blog-app.herokuapp.com';   //Production
let url = `${BASE_URL}/news`;
let articles = [];
let container = document.querySelector('#articles');

window.addEventListener("DOMContentLoaded", () => {
    renderArticles(url);
});

async function getArticles(url) {
    try {
        let res = await fetch(url);
        if (!res.ok) {
            throw new Error('Network response was not OK');
        }
        let data = await res.json();
        return data.articles ?? data;
    } catch (error) {
        console.log(error);
    }
}

async function renderArticles(url) {
    articles = await getArticles(url);
    container.innerHTML = '';
    let i = 1;
    articles.forEach(article => {
        /*if (article.description === null) {
            return;
        }*/
        article.id = i;
        let htmlSegment =
            `<div class="col">
                <div class="card h-100">
                    <a href="${article.url}" class="text-decoration-none text-body">
                        <img src="${article.urlToImage}" class="card-img-top" alt=""/>
                    </a>
                    <div class="card-body">
                        <div>
                            <small class="text-muted">${article.source.name}</small>
                            <img src="./images/save-fill.svg" class="ms-1 save-later" title="save for later" data-id="${article.id}"
                            alt="save"/>
                        </div>
                        <a href="${article.url}" class="text-decoration-none text-body">
                            <h5 class="card-title">${article.title}</h5>
                            <p class="card-text">${article.description}</p>
                        </a>
                    </div>
                    <div class="card-footer">
                        <small class="text-muted">${article.timeAgo}</small>
                    </div>
                </div>
            </div>`;

        container.innerHTML += htmlSegment;
        i++;
    });
}

const searchForm = document.querySelector(".search-form");
searchForm.addEventListener("submit", e => {
    e.preventDefault();
    let query = document.querySelector(".search-input").value;
    if (query.trim() !== '') {
        url = `${BASE_URL}/news/search?q=${query}`;
        renderArticles(url);
    }
});

const advanceBtn = document.querySelector(".advanceBtn");
advanceBtn.addEventListener("click", () => {
    let category = document.querySelector("#category").value;
    let language = document.querySelector("#language").value;
    if (category !== "" || language !== "") {
        url = `${BASE_URL}/news/advanced?category=${category}&language=${language}`;
        renderArticles(url);
    }
});

const homeLink = document.querySelector(".navbar-brand");
homeLink.addEventListener("click", (e) => {
    e.preventDefault();
    url = `${BASE_URL}/news`;
    renderArticles(url);
});

container.addEventListener("click", (e) => {
    if (e.target.classList.contains("save-later")) {
        let id = e.target.dataset.id;
        let article = articles.find(article => article.id == id);

        fetch(`${BASE_URL}/news/save`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(article),
        })
            .then((response) => {
                console.log('Success:', response.status);
                alert("Article Saved Successfully");
            })
            .catch((error) => console.error('Error:', error));
    }
});

const savedArticle = document.querySelector(".saved-article");
savedArticle.addEventListener("click", e => {
    e.preventDefault();
    url = `${BASE_URL}/news/saved/articles`;
    renderArticles(url);
});
