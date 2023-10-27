# Taller_SpringBoot

### Pasos Para Iniciar El Proyecto <br>
![imagen](https://github.com/kevinandresbenitez/Taller_SpringBoot/assets/73619016/cdf2ef0a-00cc-4d16-b65c-45b1d0b5f1f3)<br> 
O<br>
![imagen](https://github.com/kevinandresbenitez/Taller_SpringBoot/assets/73619016/432234c1-8d4f-40d6-9ac7-ce6d34da887f)<br>
En netbeans<br>

## Aclaracion<br>
Proyecto Realizado con Java 20, Gradle 8.3<br>


## Git

### Pasos para organizarse <br>
![image](https://github.com/kevinandresbenitez/Taller_uml/assets/73619016/e41108ad-8043-4d72-8cc9-8ee291c79fcc)  <br> 

1)Clonar  <br>
`git clone https://github.com/kevinandresbenitez/Taller_SpringBoot`<br>
2)Estaras situado en rama master, crea una rama development<br>
`git branch development`<br>
3)Situate en la rama development<br>
`git checkout development`<br>
3)Realiza cambios y agregalos al stage,para agregar todo en vez de un nombre pon sin las comillas:"."<br>
`git add fichero`<br>
4)Crea un commit<br>
`git commit -m'Descripcion de cambios realizados'`<br>
5)Envia los cambios a la rama development<br>
`git push origin development`<br>  
6)Una vez enviada a la rama development dirijase a pull request en el repositorio: <br>  
![image](https://github.com/kevinandresbenitez/Taller_uml/assets/73619016/1623de79-c971-4778-97ff-af0a615bced0) <br> 
7)Ir a new pull request <br>  
![image](https://github.com/kevinandresbenitez/Taller_uml/assets/73619016/0dad736c-cae6-4476-9736-edeb54db7bd0) <br> 
8)Elegir la rama development <br>  
![image](https://github.com/kevinandresbenitez/Taller_uml/assets/73619016/a4d2101e-1127-4ff3-adb7-a627b506c1ae) <br> 
9)Generar pull request, de esta manera otra persona que vea en pull request en ese apartado, podra aceptar su commit y fucionarlo con la rama principal. <br> 



### Situaciones a tener en cuenta <br>
1)Siempre trabajar sobre la rama development, la rama master permanecera intacta para poder recibir cambios que esten en el repositorio con,antes de empezar a trabajar actualize la rama master (ya que si actualiza la rama development mientras tiene cambios en su local es mas complicado solucionar los conflictos de codigo): <br>
`git pull origin master` <br>
Luego Nos situamos en la rama development: <br>
`git checkout development` <br>
Actualizamos la rama development con los cambios actualizados que se encuentran en la rama master: <br>
`git merge master` <br>
2)En caso de que tenga trabajo realizado en development a la hora de pasar los cambios de la rama master a development, puede que surgan conflictos de codigo, solo le pedira que acomode el codigo de la rama development para que funcione junto con la informacion actualizada de la rama master,luego siga trabajando como venia  
