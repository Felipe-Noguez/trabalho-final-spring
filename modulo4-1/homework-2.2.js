db.createCollection ("cupom")

//Cupom

//Inserts

db.cupom.insertMany([
    {
        "desconto": 150
    },
    {
        "desconto": 100
    },
    {
        "desconto": 50
    }
])

db.cupom.insertOne(
    {
        "desconto": 10
    }
)

//Finds

db.cupom.find({
    desconto: 100
})

db.cupom.find({
    $or:[
        {"desconto": 100},
        {"desconto": 150}
    ]
    })

//Updates

db.cupom.updateOne(
    { desconto: 50 },
    {
      $set: { desconto: 40 }           
    }
 )

 db.cupom.updateOne(
    { desconto: 40 },
    {
      $set: { desconto: 30 }           
    }
 )

 //Deletes

db.cupom.deleteOne( {desconto: 30} )
db.cupom.deleteMany({desconto: 30, desconto: 10})

//Projections

db.cupom.find({ desconto: 150}, { valor: "$desconto"}) 
db.cupom.find({ desconto: 100}, { valor: "$desconto"}) 

//Aggregates

db.cupom.aggregate( [
    { $match: { desconto: {$gt:60} } },
    { $group: { _id: "$_id", sumQuantity: {$sum: "$desconto" }} }
 ])

db.cupom.aggregate( [
    { $match: { desconto: {$lt:60} } },
    { $group: { _id: "$_id", sumQuantity: {$sum: "$desconto" }} }
])

//Produtos

//Inserts

db.produtos.insertMany([
    {
        "nome": "Copo Viagem Max Nintendo Super Mario Bros",
        "descricao": "Copo",
        "quantidade": 10,
        "tipo": 1,
        "valor": 1500,
        "id_usuario": 2 
    },

    {
        "nome": "Almofada Mario E Luigi - Produto Oficial Nintendo",
        "descricao": "Almofada",
        "quantidade": 13,
        "tipo": 2,
        "valor": 59,
        "id_usuario": 3 
    }
])


db.produtos.insertOne(
    {
    "nome": "Caneca Mágica Donkey Kong Nintendo 300ml Produto Oficial",
    "descricao": "Caneca",
    "quantidade": 8,
    "tipo": 1,
    "valor": 70,
    "id_usuario": 1 
    }
)

//Finds

db.produtos.find({valor: {$gte: 250}},
    {
        nome_tipo: {$concat: ["$nome", " de tipo: ", { $convert: { input: "$tipo", to: "string" }}]}
    })

    db.produtos.find({
        nome: {
          $regex: /mario/,
          $options: "i"
        }
      })

//Updates

db.produtos.updateOne(
    {"nome": "Almofada Mario E Luigi - Produto Oficial Nintendo"}, 
    {
    $set: {"valor": 79}
    }
)

db.produtos.updateMany(
    {"tipo": 1}, 
    {
    $set: {"valor": 800}
    }
)

//Deletes

db.produtos.deleteOne( {nome: "Pelúcia Gigante Snorlax"} )
db.produtos.deleteMany({tipo: 1, id_usuario: 2})

//Projections

db.produtos.find({ tipo: 2}, 
{
 nome: { $concat: [ "$nome", " - ", "$descricao" ] },
 quantidade: "$quantidade",
 _id: 0, tipo: 1, quantidade: 1
})

db.produtos.find({ tipo: 1}, 
    {
     nome: { $concat: [ "$nome", " - ", { $convert: { input: "$valor", to: "string" }}, " reais" ] },
     quantidade: "$quantidade",
     _id: 0, tipo: 1
    })

//Aggregates

db.produtos.aggregate([
        { $match: { tipo: 2 } },
        {$group:{
            _id: "$tipo",
            avgAmount: { $avg: "$valor" }
          }}]
 )

 db.produtos.aggregate([
    { $match: { id_usuario: 3 } },
    {$group:{
        _id: "$id_usuario",
        avgAmount: { $avg: "$valor" }
      }}]
)