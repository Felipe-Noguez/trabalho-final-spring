use pokestore

db.createCollection ("produtos")

// Inserts

db.produtos.insertOne(
    {
    "nome": "Pokemon Yelloy",
    "descricao": "Jogo para gameboy",
    "quantidade": 2,
    "tipo": 1,
    "valor": 1500,
    "id_usuario": 2 
    }
)

db.produtos.insertMany([
    {
        "nome": "Pokemon Yelloy",
        "descricao": "Jogo para gameboy",
        "quantidade": 2,
        "tipo": 1,
        "valor": 1500,
        "id_usuario": 2 
    },
    
    {
        "nome": "Pelúcia Gigante Snorlax",
        "descricao": "Pelúcia Snorlax para deitar",
        "quantidade": 23,
        "tipo": 2,
        "valor": 500,
        "id_usuario": 3 
    },

    {
        "nome": "Gameboy Advanced",
        "descricao": "Jogo para gameboy",
        "quantidade": 3,
        "tipo": 3,
        "valor": 1200,
        "id_usuario": 3 
    }
])

// Buscas

db.produtos.find(

    { "tipo": 1 }

).pretty()

db.produtos.findOne(

    { "nome": "Pokemon Yellow",
      "valor": {$gt:1000} }

)