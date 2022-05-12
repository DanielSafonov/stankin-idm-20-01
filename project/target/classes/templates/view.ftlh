<!DOCTYPE html>
<html lang="ru" class="h-100">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Table</title>

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.2.1/dist/css/bootstrap.min.css" integrity="sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS" crossorigin="anonymous">

    <style>
        .footer {
            background-color: #f5f5f5;
        }
    </style>
</head>

<body class="d-flex flex-column h-100">
<main role="main" class="flex-shrink-0">
    <div class="container mt-5">
        <div class="row m-auto">
            <h4>Таблица оборудования</h4>
            <a class="btn btn-primary ml-auto" href="/export" role="button">Эскпортировать</a>
        </div>

        <div class="table-responsive mt-2">
            <table class="table table-bordered table-hover table-sm">
                <thead class="thead-light">
                <tr>
                    <th scope="col">№</th>
                    <th scope="col">Модель</th>
                    <th scope="col">Детали</th>
                    <th scope="col">Год выпуска</th>
                </tr>
                </thead>
                <tbody>
                <#list row as i>
                    <tr>
                        <th scope="row">${i.id}</th>
                        <td>${i.model}</td>
                        <td>${i.details} мм/с</td>
                        <td>${i.year}</td>
                    </tr>
                </#list>
                </tbody>
            </table>
        </div>
        <nav aria-label="Навигация по записям таблицы">
            <ul class="pagination justify-content-center">
                <li class="page-item"><a class="page-link" href="/table?page=0">1</a></li>
                <li class="page-item"><a class="page-link" href="/table?page=1">2</a></li>
            </ul>
        </nav>
    </div>
</main>

<footer class="footer mt-auto py-3">
    <div class="container">
        <span class="text-muted">&copy; Daniel Safonov, IDM-20-01</span>
    </div>
</footer>
</body>
</html>