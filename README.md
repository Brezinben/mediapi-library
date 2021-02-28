Voici la doc de l'api, toutes les requête ci- dessous sont préfixer par : http://localhost:8080/jakartatp-1.0-SNAPSHOT/

## ``GET``

### User

All:
``api/users/``

Un seul :
``api/users/1``

Par email :
`` api/users/?email=test@test.fr``

### Loan

Tout les loans du user 1 :
``api/users/1/loans``

Un seul :
``api/users/1/loans/0``

Par status:
``api/users/1/loans?status=EN_RETARD``

Valeur posible ( *les valeurs de LoanState.java* ) :

- EN_COURS
- RENDU
- EN_RETARD

### Group

All:
``api/groups``

Un seul :
``api/groups/1``

Par nom du groupe :

    TODO

### Album

Tout les Albums du groupe 1 :
``api/groups/1/albums``

Premier Album du groupe 1 :
``api/groups/1/albums/0``

Par titre de l'album:

    TODO

### Item

Tous les items pour le premier album du groupe 1 :
``api/groups/1/albums/0/items``

Le deuxième item de la liste précédante :
``api/groups/1/albums/0/items/2``

## ``POST``

### User

````json
{
  "email": "benjamin@hotmail.fr",
  "firstName": "Benjamin",
  "lastName": "David"
}
````

Si l'email existe déjà, alors une exception sera levée.

### Loan

Sur ``/api/users/:id/loans``

````json
[
  {
    "dateStart": "2020-02-27",
    "itemId": 3
  }
]
````

On peut aller jusqu'a 5 enregistrement :

````json
[
  {
    "dateStart": "2020-02-27",
    "itemId": 5
  },
  {
    "dateStart": "2020-02-27",
    "itemId": 5
  },
  {
    "dateStart": "2020-02-27",
    "itemId": 6
  }
]
````

Ici il viendra créer des loan pour l'item 5 et 6 si l'id est identique il n'en garde qu'un.

L'utilisateur ne peux pas créer un Loan avec un Item déjà pris ou inutilisable.

### Group

Crée un groupe avec des albums :

Sur ``api/groups``

```json
{
  "createdAt": "2020-02-27",
  "name": "Manafest",
  "albums": [
    {
      "title": "Mon Super Album",
      "release": "2020-02-27"
    },
    {
      "title": "Mon Deuxième Album",
      "release": "2020-02-28"
    }
  ]
}
```

### Album

Sur ``api/groups/:id/albums``

````json
{
  "release": "2018-02-06",
  "title": "RocK or Bust"
}
````

Vous ne pourrez pas créer plusieurs albums d'un coup.

Vous ne pouvez pas mettre un album qui a le même titre sur ce groupe.

### Item

Sur ``/api/groups/:id/albums/:id/items/``

````json
[
  {
    "state": "NEUF",
    "type": "CD",
    "nombre": 10
  },
  {
    "state": "NEUF",
    "type": "VINYLE",
    "nombre": 9
  }
]
````

Viendra créer 9 items-VINYLE et 10 item-CD sur l'album.

Valeur posible pour le state ( *les valeurs de ItemState.java* ) :

- NEUF
- CONVENABLE
- ABIMER
- INUTILISABLE

Valeur posible pour le state ( *les valeurs de ItemType.java* ) :

- CD
- VINYLE
- CASSETTE

## ``PUT``

### User

    TODO

### Loan

    TODO

### Group

    TODO

### Album

    TODO

### Item

    TODO

## ``DELETE``

### User

    TODO

### Loan

    TODO

### Group

    TODO

### Album

    TODO

### Item

    TODO


