# :link: Como usar

### base de url: `http://api.techdecode` (indisponivel por enquanto)
### Seções:

| Usuario 👨‍🦱                                   | Noticias 🗞️                                      |
|-------------------------------------------------|------------------------------------------------|
| [Adicionar novo usuario](#criarconta)           | [Ver todas as noticias](#vertodas)             |
| [Adicionar novo usuario admin](#criacontaadmin) | [Ver detalhes da noticia](#verdetalhenoticia)  |
| [Fazer login](#entrar)                          | [Ver noticia por categoria](#noticiacategoria) |
|                                                 | [Procurar por noticia](#search)                |
|                                                 | [Criar noticia](#criarnoticia)                 |
|                                                 | [Editar noticia](#editarnoticia)               |

## User 👨‍🦱

<h3 id="criarconta">Adicionar novo usuario comum</h3>

#### Requisição
- method `POST`
- rota `/auth/signUp`
- body:
```json
{
  "name": "string",
  "email": "string",
  "password": "string"
}
```

#### Respostas
- `status: 201` criado com sucesso:
```json
{
  "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
  "name": "string",
  "email": "string",
  "password": "string"
}
```


- `status: 409` titulo da noticia já existe
```json
{
  "title":  "conflict",
  "message": "string",
  "status": 409
}
```
#
<h3 id="criacontaadmin">Adicionar novo usuario admin</h3>

#### Requisição
- method `POST`
- rota `/auth/signUp/admin`
- headers: `Authorization: Bearer token` token com autorização de admin
- body:
```json
{
  "name": "string",
  "email": "string",
  "password": "string"
}
```

#### Respostas
- `status: 201` criado com sucesso:
```json
{
  "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
  "name": "string",
  "email": "string",
  "password": "string"
}
```


- `status: 409` titulo da noticia já existe
```json
{
  "title":  "conflict",
  "message": "string",
  "status": 409
}
```
- `status: 403` não autorizado
#
<h3 id="entrar">Fazer login</h3>

#### Requisição
- method `POST`
- rota `/auth/signIn`
- body:
```json
{
  "email": "string",
  "password": "string"
}
```

#### Respostas
- `status: 200` logado:
```json
{
  "access_token": "string"
}
```

- `status: 403` não autorizado
#

## Noticias 🗞️

<h3 id="vertodas">Ver todas as noticias</h3>

#### Requisição
- method `GET`
- rota `/post`

#### Respostas
- `status: 200` noticias encontradas:
```json
[
  {
    "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
    "title": "string",
    "bannerUrl": "string",
    "date_at": "string"
  }
]
```

- `status: 404` não encontrado nenhuma noticia
```json
{
  "title":"not found",
  "message":"a lista de postagem está vazia",
  "status":404
}
```
#

<h3 id="verdetalhenoticia">Ver detalhes da noticia</h3>

#### Requisição
- method `GET`
- rota `/post/{id}`

#### Respostas
- `status: 200` noticias encontradas:
```json
{
"id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
"title": "string",
"bannerUrl": "string",
"date_at": "string",
"descriptions": "string",
"font": "string",
"comments": [
  {
    "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
    "username": "string",
    "comment": "string",
    "date_at": "string"
  }
]
}
```

- `status: 404` não encontrado nenhuma noticia
```json
{
  "title":"not found",
  "message":"postagem não encontrada",
  "status":404
}
```
#

<h3 id="noticiacategoria">Ver noticias por categoria</h3>

#### Requisição
- method `GET`
- rota `/post/category/{category_id}`

#### Respostas
- `status: 200` noticias encontradas:
```json
[
  {
    "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
    "title": "string",
    "bannerUrl": "string",
    "date_at": "string"
  }
]
```

- `status: 404` não encontrado nenhuma categoria
```json
{
  "title":"not found",
  "message":"categoria não existe",
  "status":404
}
```

#

<h3 id="search">Buscar por noticias</h3>

#### Requisição
- method `GET`
- rota `/post/search/{search}`

#### Respostas
- `status: 200` noticias encontradas:
```json
[
  {
    "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
    "title": "string",
    "bannerUrl": "string",
    "date_at": "string"
  }
]
```

- `status: 404` não encontrado nenhuma noticia
```json
{
  "title":"not found",
  "message":"nenhuma noticia encontrada",
  "status":404
}
```
#

<h3 id="criarnoticia">Criar noticia</h3>
#### Requsições
- method: `POST`
- rota: `/post`
- body: multpart/form-data

| chave       | valor    |
|-------------| -------- |
| photo       | data     |
| title       | string   |
| description | string   |
| font        | string   |
| category_id | string   |

#### Respostas:

- `status: 201` Noticia criada com sucesso
```json
{
  "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
  "title": "string",
  "bannerUrl": "string",
  "description": "string",
  "font": "string",
  "date_at": "string"
}
```

- `status: 409` Já existe uma postagem com esse titulo
```json
{
  "title":"string",
  "message":"string",
  "status":409
}
```

#

<h3 id="deletarnoticia">Deletar noticia</h3>
#### Requisição:
- method: `DELETE`
- rota: `/post/{id}`

#### Respostas
- `status: 200` noticia deletada
- `status: 404` noticia não existe mais

#

<h3 id="editarnoticia">Editar noticia</h3>
#### Requsições
- method: `PUT`
- rota: `/post/{id}`
- body: multpart/form-data

| chave       | valor    |
|-------------| -------- |
| photo       | data     |
| title       | string   |
| description | string   |
| font        | string   |
| category_id | string   |

#### Respostas:

- `status: 200` Noticia alterada
```json
{
  "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
  "title": "string",
  "bannerUrl": "string",
  "description": "string",
  "font": "string",
  "date_at": "string"
}
```

- `status: 409` Já existe uma postagem com esse titulo
```json
{
  "title":"string",
  "message":"string",
  "status":409
}
```
- `status: 404` noticia não existe mais