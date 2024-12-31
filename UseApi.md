# :link: Como usar

### base de url: `http://localhost:8080` (local por enquanto)
### Seções:
- [Adicionar novo usuario](#criarconta)
- [Adicionar novo usuario admin](#criacontaadmin)
- [Fazer login](#entrar)

## User

<h3 id="criarconta">Adicionar novo usuario</h3>

#### Requisição
- só será autorizado a fazer essa requisição
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