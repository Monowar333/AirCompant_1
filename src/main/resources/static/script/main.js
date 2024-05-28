function loadMenu() {
          requestUser = new XMLHttpRequest();
          requestUser.open("GET", "http://localhost:8080/public/currentRole");
          requestUser.send(null);

          requestUser.onreadystatechange = function () {
            if (requestUser.readyState == 4 && requestUser.status == 200) {
               var data = requestUser.responseText;
               var nav = document.getElementById("navbarNav");
               var form = document.createElement('form');
               form.classList.add("orm-inline");
               form.classList.add("my-2");
               form.classList.add("my-lg-0");

               if (data == 'NOT_AUTHORIZED') {
                 form.innerHTML = '<a href="login" class="btn btn-outline-success my-4 my-sm-0">Login</a>'
               } else {
                 form.innerHTML = '<a href="logout" class="btn btn-outline-success my-4 my-sm-0">Logout</a>'

                 var navUl = document.getElementById("ulItems");
                 var li = document.createElement('li');
                 li.classList.add("nav-link");
                 if (data == 'ADMIN') {
                    li.innerHTML = '<a class="nav-link" aria-current="page" href="admin_cabinet.html">Особистий Кабінет</a>'
                 } else {
                    li.innerHTML = '<a class="nav-link" aria-current="page" href="user_cabinet.html">Особистий Кабінет</a>'
                 }
                 navUl.appendChild(li)
               }

               nav.appendChild(form)
            }

          }
}