const url="http://localhost:8080/autor";

function hideloader(){
    document.getElementById("loading").style.display="none";
}

function show(dataAuthor){
    let tab = `<thead>
                <th scope="col">#</th>
                <th scope="col">Name</th>
                <th scope="col">Surname</th>
                <th scope="col">Cellphone</th>
                 <th scope="col">E-mail</th>
                <th scope="col">Birth Date</th>
            </thead>`
    for(let data of dataAuthor){
        tab += ` <tr>
                    <tb scope="row">${data.id}</td>
                    <td>${data.name}</td>
                    <td>${data.surname}</td>
                    <td>${data.cellphone}</td>
                    <td>${data.email}</td>
                    <td>${data.birthDate}</td>
                </tr>`
    }
    document.getElementById("authorData").innerHTML = tab;
}

async function getAPI(url){
    const response = await fetch(url, {method: "GET"});

    var data = await response.json();
    console.log(data);
    if(response.ok)
        hideloader();
    show(data)
}

getAPI(urlAuthor)