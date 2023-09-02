import java.io.IOException;
import java.nio.file.*;
import java.security.KeyPairGenerator;
import java.security.Signature;
import java.util.Scanner;
import javax.crypto.Cipher;

class manager {
  static String storedPass = "1234";
  static Path storedPassFile = Paths.get("Pass.csv");
  

  private static String encryptPass(String pass) {
    return pass;
  }

  private static String decryptPass(String pass) {
    return pass;
  }

  private static void addPass(String tag, String pass) {
    String line = tag + "," + pass + "\n";
    try {
      Files.write(storedPassFile, line.getBytes(), StandardOpenOption.APPEND);
      System.out.print("\033[H\033[2J");
      System.out.println("Contraseña añadida correctamente.");
    } catch (Exception e) {
      System.out.print("\033[H\033[2J");
      System.out.println("Error al añadir la contraseña.");
      return;
    }
  }
  private static void showPass(){
    try (Scanner scanner = new Scanner(storedPassFile)) {
      scanner.nextLine();
      String[] currentPage = new String[4];
      String completo = "";
      int i = 1;
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        String[] parts = line.split(",");
        currentPage[i - 1] = parts[1];
        completo = completo + i + ". Tag: " + parts[0] + "\n";
        i++;
        if(i == 5){
          i = 1;
          System.out.println(completo);
          completo = "";
          String opcion = System.console().readLine();
          if(opcion.equals("1") || opcion.equals("2") || opcion.equals("3") || opcion.equals("4")){
            System.out.println("Contraseña: " + currentPage[Integer.parseInt(opcion) - 1]);
            System.console().readLine();
            System.out.print("\033[H\033[2J");
          }
        }
        System.out.print("\033[H\033[2J");
      }
      if(!completo.equals("")){
        System.out.println(completo);
        System.console().readLine();
        System.out.print("\033[H\033[2J");
      }
    } catch (Exception e) {
      System.out.println("Error al leer el archivo de contraseñas. (Muchas)");
      return;
    }
  }

  private static void getPass(String tag) {
    System.out.print("\033[H\033[2J");
    try (Scanner scanner = new Scanner(storedPassFile)) {
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        String[] parts = line.split(",");
        if (parts[0].equals(tag)) {
          System.out.println("Tag: " + tag + "\nContraseña: " + parts[1]);
          System.console().readLine();
          System.out.print("\033[H\033[2J");
          scanner.close();
          return;
         }
       }
       System.out.println("No se encontro la contraseña con este tag.");
    } catch (Exception e) {
      System.out.println("Error al leer el archivo de contraseñas.");
      return;
    }
  }

  private static void newPass() {
    System.out.println("Quiere añadir o genera una contraseña?");
    Boolean flag = true;
    while (flag) {
      System.out.println("1. Añadir");
      System.out.println("2. Generar");
      System.out.println("3. Salir");
      String option = System.console().readLine();
      switch (option) {
        case "1":
          System.out.print("Por favor ingrese un tag para la contraseña:");
          String tag1 = System.console().readLine();
          System.out.print("Por favor ingrese la contraseña:");
          String pass = System.console().readLine();
          System.out.println("Confimer la contraseña:");
          String pass2 = System.console().readLine();
          if (pass.equals(pass2)) {
            addPass(tag1, pass);
          } else {
            System.out.println("Las contraseñas no coinciden, por favor intente de nuevo.");
          }
          break;
        case "2":
          System.out.print("Por favor ingrese un tag para la contraseña:");
          String tag2 = System.console().readLine();
          String tempPass = genPass();
          System.out.println("La contraseña generada es: " + tempPass + " para el tag: " + tag2);
          addPass(tag2, tempPass);
        case "3":
          flag = false;
          break;

        default:
          System.out.println("Opcion invalida, por favor intente de nuevo.");
          break;
      }
    }
  }

  private static String genPass() {
    return "1234";
  }

  private static void checkPass() {
    Boolean flag = true;
    while (flag){
      System.out.println("Que desea hacer?");
      System.out.println("1. Mostrar contraseñas");
      System.out.println("2. Buscar contraseña");
      System.out.println("3. Volver");
      String opcion = System.console().readLine();
      switch (opcion) {
        case "1":
          System.out.println("Mostrando contraseñas...");
          showPass();
          break;
        case "2":
          System.out.println("Por favor ingrese el tag de la contraseña que desea buscar:");
          String tag = System.console().readLine();
          System.out.println("Buscando contraseña...");
          getPass(tag);
          break;
        case "3":
          flag = false;
          break;
        default:
          System.out.println("Opcion invalida, por favor intente de nuevo.");
          break;
      }
    }
    
    
  }

  public static void main(String[] args) {
    System.out.println("Bienvenido!");
    if (!Files.exists(storedPassFile)) {
      System.out.println("Usuario nuevo detectado, creando archivo de contraseñas...");
      Boolean flag = true;
      while (flag) {
        System.out.println("Por favor ingrese la contraseña nueva:");
        String pass = System.console().readLine();
        System.out.println("Por favor confirme la contraseña:");
        String pass2 = System.console().readLine();
        if (pass.equals(pass2)) {
          try {
            Files.createFile(storedPassFile);
            try {
              String f = pass + "\n";
              Files.write(storedPassFile, f.getBytes(), StandardOpenOption.APPEND);
            } catch (Exception e) {
              System.out.println("Error al añadir la contraseña.");
              return;
            }
            flag = false;
          } catch (Exception e) {
            System.out.println("Error al crear el archivo de contraseñas.");
            return;
          }
        } else {
          System.out.println("Las contraseñas no coinciden, por favor intente de nuevo.");
        }
      }
    }
    System.out.print("\033[H\033[2J");
    Boolean flag = true;
    while (flag) {
      System.out.println("Por favor ingrese la contraseña para entrar:");
      String pass = System.console().readLine();
      if (pass.equals(storedPass)) {
        flag = false;
      } else {
        System.out.println("Contraseña incorrecta, por favor intente de nuevo.");
      }
    }
    System.out.print("\033[H\033[2J");
    // ------------------Desde aqui es el flujo normal del programa-------------------
    flag = true;
    System.out.println("Bienvenido al gestor de contraseñas, que desea hacer?");
    while (flag) {
      System.out.println("1. Crear nueva contraseña");
      System.out.println("2. Ver contraseñas guardadas");
      System.out.println("3. Salir");
      String option = System.console().readLine();

      switch (option) {
        case "1":
          System.out.print("\033[H\033[2J");
          newPass();
        case "2":
          System.out.print("\033[H\033[2J");
          checkPass();
        case "3":
          flag = false;
          break;
        default:
          System.out.println("Opcion invalida, por favor intente de nuevo.");
          break;
      }
    System.out.print("\033[H\033[2J");

    }
  }
}