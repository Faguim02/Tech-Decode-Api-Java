#
<div align="center">
    <img src="assets/logo.png" alt="logo"/>
</div>

<h3 align="justify">
    TechDecode é uma API RestFull web gratuita para obter informações de noticías referente a tecnologias do site TechDecode.
</h3> 

## Uso

### base de url: `http://localhost:8080` (local por enquanto)

## Endpoints
    
### - acessar todas as noticias `GET` `/post`
#### Response `OK` `200`:
```json
[
    {
        "id": "807f6d10-0530-454c-8aa0-b0d5cafc15c7",
        "title": "New ias",
        "bannerUrl": "aa",
        "date_at": "12/12/2001"
    },
    {
        "id": "811c3005-c88c-456c-94e4-316c1b275cdf",
        "title": "Novidade",
        "bannerUrl": "aass",
        "date_at": "29/07/2024"
    }
]
```

#

### - acessar detalhes de uma unica noticías `get` `/post/{id}` 

#### Response `OK` `200`:
```json
{
  "id": "807f6d10-0530-454c-8aa0-b0d5cafc15c7",
  "title": "New ias",
  "bannerUrl": "aa",
  "description": "aaaa",
  "font": "eu",
  "date_at": "12/12/2001",
  "comments": [
    {
      "id": "fc8ba455-eb99-4334-8392-c49a829067ca",
      "name": "Fagner",
      "comment": "oi",
      "date": "00/00/0000"
    },
    {
      "id": "75d8245d-8756-4bd7-9597-0bace9716d08",
      "name": "Fagner",
      "comment": "oi",
      "date": "00/00/0000"
    }
  ]
}
```

#

### - criar comentarios em uma noticia `post` `/comment`
#### Body:
```json
{
  "name": "seu nome",
  "comment": "comentario",
  "post_id": "807f6d10-0530-454c-8aa0-b0d5cafc15c7"
}
```
#### Response `OK` `200`:
```json
{
    "id": "75d8245d-8756-4bd7-9597-0bace9716d08",
    "name": "seu nome",
    "comment": "comentario",
    "date": "00/00/0000"
}
```