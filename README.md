
# video-share-aluraflix

This project is a challenge proposed by Alura through the #alurachallengeback

![java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Spring](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![postgres](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![linkedin](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white?link=https://www.linkedin.com/in/carolina-freire-ap/)

What's this?

This API creates, updates, deletes, and searches for all videos or by ID.

## Installation

1. Java 8
2. Maven
3. Postgres

## API Reference

#### Creates video

```http
  POST /video
```
Body
```json
{
	"title": "Teste",
	"description": "novo video incluído",
	"urlVideo": "http://www.carol.com.br"
}
```
#### Get all Videos

```http
  GET /videos
```
Response
```
[
  {
    "id": 34,
    "title": "teste",
    "description": "novo video incluído",
    "urlVideo": "http://www.carol.com.br"
  },
  {
    "id": 35,
    "title": "teste",
    "description": "novo video incluído",
    "urlVideo": "http://www.carol.com.br"
  }
]
```
#### Get Video by ID

```http
  GET /video/{id}
```
Response

```json
{
  "id": 33,
  "title": "teste 3333",
  "description": "novo video incluído",
  "urlVideo": "http://www.carol.com.br"
}
```
#### Update Video

```http
  PUT /video/{id}
```
Body

```json
{
	  "title": "teste 66666",
    "description": "novo video incluído",
    "urlVideo": "http://www.carol.com.br"
}
```

```http
  DEL /video/{id}
```
Body

```json
{
	
}
```
## Authors

- [@carolina-freire-ap](https://www.linkedin.com/in/carolina-freire-ap/)

  