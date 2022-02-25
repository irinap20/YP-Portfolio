  
 window.addEventListener("scroll", function(){
   var header = document.querySelector("header");
   var bar = document.querySelector("bar");
   var burger = document.querySelector("hamburger");
   header.classList.toggle("sticky", window.scrollY > 0);
   bar.classList.toggle("sticky", window.scrollY>0);
   burger.classList.toggle("sticky", window.scrollY>0);
 })  

 
 