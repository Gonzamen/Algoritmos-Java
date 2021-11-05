import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import uy.edu.ort.obligatorio.ISistema;
import uy.edu.ort.obligatorio.Sistema;
import uy.edu.ort.obligatorio.Retorno;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public
class TestObligatorio
{

    private
    ISistema implementacionSistema(){
        return new Sistema();
    }

    private
    ISistema implementacionSistema(int puntos){
        ISistema sistema=this.implementacionSistema();
        sistema.inicializarSistema(puntos);
        return sistema;
    }

    @Test
    public void testInicializar_ok(){
        ISistema sistema=this.implementacionSistema();
        assertEquals(Retorno.Resultado.OK,
                     sistema.inicializarSistema(100).resultado);
        assertEquals(Retorno.Resultado.OK,
                     sistema.destruirSistema().resultado);
    }

    @Test
    public void testInicializar_puntos_0_error(){
        ISistema sistema=this.implementacionSistema();
        assertEquals(Retorno.Resultado.ERROR_1,
                     sistema.inicializarSistema(0).resultado);

    }
    @Test
    public void testInicializar_puntos_negativos_error(){
        ISistema sistema=this.implementacionSistema();
        assertEquals(Retorno.Resultado.ERROR_1,
                     sistema.inicializarSistema(-3).resultado);

    }

    @Test
    public void testRegistarObreros_ok(){
        ISistema sistema=this.implementacionSistema(100);
        String cedula1="3.244.222-3",nombre1="Ramon";
        String cedula2="244.222-3",nombre2="Roberto";
        String cedula3="6.244.221-1",nombre3="Claudia";
        assertEquals(Retorno.Resultado.OK,
                     sistema.registrarObrero(cedula1,nombre1).resultado);
        assertEquals(Retorno.Resultado.OK,
                     sistema.registrarObrero(cedula2,nombre2).resultado);
        assertEquals(Retorno.Resultado.OK,
                     sistema.registrarObrero(cedula3,nombre3).resultado);

        assertEquals(cedula1+";"+nombre1,sistema.buscarObrero(cedula1).valorString);
        assertEquals(cedula2+";"+nombre2,sistema.buscarObrero(cedula2).valorString);
        assertEquals(cedula3+";"+nombre3,sistema.buscarObrero(cedula3).valorString);
        assertEquals(Retorno.Resultado.OK,
                     sistema.destruirSistema().resultado);
    }

    @Test
    public void testRegistrarObreros_cedulaInvalida_error(){
        ISistema sistema=this.implementacionSistema(100);
        List<String> cedulasInvalidas=List.of("129.322.222-0",
                                              "4.342.322-0unstring",
                                              "111-0",
                                              "1.111-0",
                                              "unemail@gmail.com");
        for (String cedulaInvalida: cedulasInvalidas)
        {
            assertEquals(Retorno.Resultado.ERROR_1,sistema.registrarObrero(cedulaInvalida,"Un nombre").resultado,"Esperaba que la cedula no fuera valida para:"+cedulaInvalida);

            assertEquals(Retorno.Resultado.ERROR_1,sistema.buscarObrero(cedulaInvalida).resultado,"Esperaba que la cedula no fuera valida para:"+cedulaInvalida);

        }
        sistema.destruirSistema();
    }

    @Test
    public void testRegistrarObrero_duplicado_error(){
        ISistema sistema=this.implementacionSistema(100);
        String cedula1="3.233.222-0";
        String cedula2="2.233.222-0";
        String cedula3="2.433.122-0";
        String cedula4="1.233.122-0";
        String cedula5="1.933.532-0";
        String cedula6="233.222-0";
        assertEquals(Retorno.Resultado.OK,
                     sistema.registrarObrero(cedula1,"Un nombre").resultado);
        assertEquals(Retorno.Resultado.OK,
                     sistema.registrarObrero(cedula2,"Un nombre").resultado);
        assertEquals(Retorno.Resultado.OK,
                     sistema.registrarObrero(cedula3,"Un nombre").resultado);
        assertEquals(Retorno.Resultado.OK,
                     sistema.registrarObrero(cedula4,"Un nombre").resultado);
        assertEquals(Retorno.Resultado.OK,
                     sistema.registrarObrero(cedula5,"Un nombre").resultado);
        assertEquals(Retorno.Resultado.OK,
                     sistema.registrarObrero(cedula6,"Un nombre").resultado);
        assertEquals(Retorno.Resultado.ERROR_2,
                     sistema.registrarObrero(cedula6,"Un nombre").resultado);

        sistema.destruirSistema();
    }

    @Test
    public void testBuscarObreros_ok(){
        ISistema sistema=this.implementacionSistema(100);
        String cedula1="1.544.222-3",nombre1="Nina";
        String cedula2="244.222-3",nombre2="Raul";
        String cedula3="2.244.221-1",nombre3="Ernesto";
        assertEquals(Retorno.Resultado.OK,
                     sistema.registrarObrero(cedula1,nombre1).resultado);
        assertEquals(Retorno.Resultado.OK,
                     sistema.registrarObrero(cedula2,nombre2).resultado);
        assertEquals(Retorno.Resultado.OK,
                     sistema.registrarObrero(cedula3,nombre3).resultado);

        assertEquals(cedula1+";"+nombre1,sistema.buscarObrero(cedula1).valorString);
        assertEquals(cedula2+";"+nombre2,sistema.buscarObrero(cedula2).valorString);
        assertEquals(cedula3+";"+nombre3,sistema.buscarObrero(cedula3).valorString);
        assertEquals(Retorno.Resultado.OK,
                     sistema.destruirSistema().resultado);
    }
    @Test
    public void testBuscarObreros_numero_operaciones_1_ok(){
        ISistema sistema=this.implementacionSistema(100);
        String [] cedulasOrdenadas=new String[]{
            "222.322-3",//0
            "800.322-3",//1
            "1.544.222-3",//2
            "2.322.222-5",//3
            "4.552.322-2",//4
            "5.211.322-2",//5
            "6.112.111-1"//6
        };
        int[] ordenInsercion=new int[]{
            3,
            1,
            0,
            2,
            5,
            4,
            6
        };
        int[] cantidadEsperada=new int[]{
            1,
            2,
            3,
            3,
            2,
            3,
            3
        };

        for (int i=0;i<cedulasOrdenadas.length;i++){
            assertEquals(Retorno.Resultado.OK,
                         sistema.registrarObrero(cedulasOrdenadas[ordenInsercion[i]],null).resultado);
        }

        for(int i=0;i<cedulasOrdenadas.length;i++){
            assertEquals(cantidadEsperada[i],sistema.buscarObrero(cedulasOrdenadas[ordenInsercion[i]]).valorEntero);

        }

    }
    @Test
    public void testBuscarObreros_numero_operaciones_2_ok(){
        ISistema sistema=this.implementacionSistema(100);
        String [] cedulasOrdenadas=new String[]{
              "222.322-3",
              "800.322-3",
              "1.544.222-3",
              "2.322.222-5",
              "4.552.322-2",
              "5.211.322-2",
            "6.112.111-1"
        };

        for (int i=0;i<cedulasOrdenadas.length;i++){
            assertEquals(Retorno.Resultado.OK,
                         sistema.registrarObrero(cedulasOrdenadas[i],null).resultado);
        }

        for(int i=0;i<cedulasOrdenadas.length;i++){
            assertEquals(i+1,sistema.buscarObrero(cedulasOrdenadas[i]).valorEntero);

        }

    }

    @Test
    public void testBuscarObrero_no_existente_error(){
        ISistema sistema=this.implementacionSistema(100);
        String cedula1="3.233.222-0";
        String cedula2="2.233.222-0";
        String cedula3="2.433.122-0";
        String cedula4="1.233.122-0";
        String cedula5="1.933.532-0";
        String cedula6="233.222-0";
        assertEquals(Retorno.Resultado.OK,
                     sistema.registrarObrero(cedula1,"Un nombre").resultado);
        assertEquals(Retorno.Resultado.OK,
                     sistema.registrarObrero(cedula2,"Un nombre").resultado);
        assertEquals(Retorno.Resultado.OK,
                     sistema.registrarObrero(cedula3,"Un nombre").resultado);
        assertEquals(Retorno.Resultado.OK,
                     sistema.registrarObrero(cedula4,"Un nombre").resultado);
        assertEquals(Retorno.Resultado.OK,
                     sistema.registrarObrero(cedula5,"Un nombre").resultado);
        assertEquals(Retorno.Resultado.OK,
                     sistema.registrarObrero(cedula6,"Un nombre").resultado);

        assertEquals(Retorno.Resultado.ERROR_2,
                     sistema.buscarObrero("1.322.333-0").resultado);

        sistema.destruirSistema();
    }

    @Test
    public void test_listar_obreros_ok(){
        ISistema sistema=this.implementacionSistema(100);

        String cedula6="233.222-0",nombre6="Nombre 6";
        String cedula4="1.233.122-0",nombre4="Nombre 4";
        String cedula2="2.433.122-8",nombre2=null;
        String cedula3="2.433.122-2",nombre3="Nombre 3";
        String cedula5="2.433.123-0",nombre5="Nombre 5";
        String cedula1="3.233.222-0",nombre1="Nombre 1";
        assertEquals(Retorno.Resultado.OK,
                     sistema.registrarObrero(cedula1,nombre1).resultado);
        assertEquals(Retorno.Resultado.OK,
                     sistema.registrarObrero(cedula2,nombre2).resultado);
        assertEquals(Retorno.Resultado.OK,
                     sistema.registrarObrero(cedula3,nombre3).resultado);
        assertEquals(Retorno.Resultado.OK,
                     sistema.registrarObrero(cedula4,nombre4).resultado);
        assertEquals(Retorno.Resultado.OK,
                     sistema.registrarObrero(cedula5,nombre5).resultado);
        assertEquals(Retorno.Resultado.OK,
                     sistema.registrarObrero(cedula6,nombre6).resultado);

        Retorno resultado=sistema.listarObreros();
        assertEquals(Retorno.Resultado.OK, resultado.resultado);
        assertEquals(
            String.format("%s|%s|%s|%s|%s|%s",
                serializarObrero(cedula6,nombre6),
                serializarObrero(cedula4,nombre4),
                serializarObrero(cedula3,nombre3),
                serializarObrero(cedula2,nombre2),
                serializarObrero(cedula5,nombre5),
                serializarObrero(cedula1,nombre1)),resultado.valorString);

    sistema.destruirSistema();
    }

    @Test
    public void testRegistrarPoste_ok(){
        ISistema sistema=this.implementacionSistema(100);
        assertEquals(Retorno.Resultado.OK, sistema.registrarPoste(10,20,"Test poste").resultado);
        assertEquals(Retorno.Resultado.OK, sistema.registrarPoste(20,40,"Poste 2").resultado);

        assertEquals(Retorno.Resultado.OK, sistema.registrarTramo(
            20,40,
            10,20,
            15).resultado);

    }
    @Test
    public void testRegistrarPoste_negativo_ok(){
        ISistema sistema=this.implementacionSistema(100);
        assertEquals(Retorno.Resultado.OK, sistema.registrarPoste(-15,20,"Test poste").resultado);
        assertEquals(Retorno.Resultado.OK, sistema.registrarPoste(20,-40,"Poste 2").resultado);

        assertEquals(Retorno.Resultado.OK, sistema.registrarTramo(
            -15,20,
            20,-40,
            15).resultado);

    }

    @Test
    public void testRegistrarPoste_existente_error(){
        ISistema sistema=this.implementacionSistema(100);
        assertEquals(Retorno.Resultado.OK, sistema.registrarPoste(-15,20,"Test poste").resultado);

        assertEquals(Retorno.Resultado.ERROR_2, sistema.registrarPoste(-15,20,"Test poste").resultado);
    }
    @Test
    public void testRegistrarPoste_max_puntos_error(){
        ISistema sistema=this.implementacionSistema(100);
        for(int i=0;i<100;i++)
            assertEquals(Retorno.Resultado.OK, sistema.registrarPoste(i,20,"Test poste").resultado);

        assertEquals(Retorno.Resultado.ERROR_1, sistema.registrarPoste(-15,20,"Superada la cantidad").resultado);
    }
    @Test
    public void testRegistrarTramo_ok(){
        ISistema sistema=this.implementacionSistema(100);
        assertEquals(Retorno.Resultado.OK, sistema.registrarPoste(-15,20,"Test poste").resultado);
        assertEquals(Retorno.Resultado.OK, sistema.registrarPoste(20,-40,"Poste 2").resultado);

        assertEquals(Retorno.Resultado.OK, sistema.registrarTramo(
            -15,20,
            20,-40,
            15).resultado);
    }
    @Test
    public void testRegistrarTramo_duplicado_error(){
        ISistema sistema=this.implementacionSistema(100);
        assertEquals(Retorno.Resultado.OK, sistema.registrarPoste(-15,20,"Test poste").resultado);
        assertEquals(Retorno.Resultado.OK, sistema.registrarPoste(20,-40,"Poste 2").resultado);

        assertEquals(Retorno.Resultado.OK, sistema.registrarTramo(
            -15,20,
            20,-40,
            15).resultado);
        assertEquals(Retorno.Resultado.ERROR_3, sistema.registrarTramo(
            -15,20,
            20,-40,
            15).resultado);
        assertEquals(Retorno.Resultado.ERROR_3, sistema.registrarTramo(
            20,-40,
            -15,20,
            15).resultado);
    }

    @Test
    public void testRegistrarTramo_no_existente_error(){
        ISistema sistema=this.implementacionSistema(100);
        assertEquals(Retorno.Resultado.OK, sistema.registrarPoste(-15,20,"Test poste").resultado);
        assertEquals(Retorno.Resultado.OK, sistema.registrarPoste(20,-40,"Poste 2").resultado);

        assertEquals(Retorno.Resultado.ERROR_2, sistema.registrarTramo(
            -15,20,
            -15,-40,
            15).resultado);
        assertEquals(Retorno.Resultado.ERROR_2, sistema.registrarTramo(
            -15,-40,
             20,20,
            15).resultado);
        assertEquals(Retorno.Resultado.ERROR_2, sistema.registrarTramo(
            15,-40,
             -15,20,
            15).resultado);
    }
    @Test
    public void testRegistrarTramo_metros_negativos_error(){
        ISistema sistema=this.implementacionSistema(100);
        assertEquals(Retorno.Resultado.OK, sistema.registrarPoste(-15,20,"Test poste").resultado);
        assertEquals(Retorno.Resultado.OK, sistema.registrarPoste(20,-40,"Poste 2").resultado);

        assertEquals(Retorno.Resultado.ERROR_1, sistema.registrarTramo(
            -15,20,
            20,-40,
            0).resultado);
        assertEquals(Retorno.Resultado.OK, sistema.registrarTramo(
            -15,20,
            20,-40,
            10).resultado);
        assertEquals(Retorno.Resultado.ERROR_1, sistema.registrarTramo(
            -15,20,
            20,-40,
            -10).resultado);

    }
    @Test
    public void testActualizarTramo_ok(){
        ISistema sistema=this.implementacionSistema(100);
        assertEquals(Retorno.Resultado.OK, sistema.registrarPoste(-15,20,"Test poste").resultado);
        assertEquals(Retorno.Resultado.OK, sistema.registrarPoste(20,-40,"Poste 2").resultado);
        assertEquals(Retorno.Resultado.OK, sistema.registrarPoste(10,-40,"Poste 2").resultado);
        assertEquals(Retorno.Resultado.OK, sistema.registrarPoste(220,-15,"Poste 2").resultado);
        assertEquals(Retorno.Resultado.OK, sistema.registrarPoste(250,-140,"Poste 2").resultado);


        assertEquals(Retorno.Resultado.OK, sistema.registrarTramo(
            -15,20,
            20,-40,
            10).resultado);
        assertEquals(Retorno.Resultado.OK, sistema.actualizarTramo(
            -15, 20,
            20, -40,
            25, ISistema.Estado.BUENO).resultado);
    }
    @Test
    public void testActualizarTramo_metros_negativos_error(){
        ISistema sistema=this.implementacionSistema(100);
        assertEquals(Retorno.Resultado.OK, sistema.registrarPoste(-15,20,"Test poste").resultado);
        assertEquals(Retorno.Resultado.OK, sistema.registrarPoste(20,-40,"Poste 2").resultado);
        assertEquals(Retorno.Resultado.OK, sistema.registrarPoste(10,-40,"Poste 2").resultado);
        assertEquals(Retorno.Resultado.OK, sistema.registrarPoste(220,-15,"Poste 2").resultado);
        assertEquals(Retorno.Resultado.OK, sistema.registrarPoste(250,-140,"Poste 2").resultado);


        assertEquals(Retorno.Resultado.OK, sistema.registrarTramo(
            -15,20,
            20,-40,
            10).resultado);
        assertEquals(Retorno.Resultado.ERROR_1, sistema.actualizarTramo(
            -15, 20,
            20, -40,
            -10, ISistema.Estado.BUENO).resultado);
    }
    @Test
    public void testActualizarTramo_null_estado_error(){
        ISistema sistema=this.implementacionSistema(100);
        assertEquals(Retorno.Resultado.OK, sistema.registrarPoste(-15,20,"Test poste").resultado);
        assertEquals(Retorno.Resultado.OK, sistema.registrarPoste(20,-40,null).resultado);
        assertEquals(Retorno.Resultado.OK, sistema.registrarPoste(10,-40,"").resultado);
        assertEquals(Retorno.Resultado.OK, sistema.registrarPoste(220,-15,"Poste 2").resultado);
        assertEquals(Retorno.Resultado.OK, sistema.registrarPoste(250,-140,"Poste 2").resultado);


        assertEquals(Retorno.Resultado.OK, sistema.registrarTramo(
            -15,20,
            20,-40,
            10).resultado);
        assertEquals(Retorno.Resultado.ERROR_1, sistema.actualizarTramo(
            -15, 20,
            20, -40,
            10,null).resultado);
    }

    private class PosteDesearlizado{
        double x;
        double y;
        String nombre;

        public
        PosteDesearlizado(final double x,
                          final double y,
                          final String nombre)
        {
            this.x = x;
            this.y = y;
            this.nombre = nombre;
        }

        @Override
        public
        String toString()
        {
            return String.format("(%.2f,%.2f,%s)",x,y,nombre);
        }

        @Override
        public
        boolean equals(final Object o)
        {
            if (this == o)
            {
                return true;
            }
            if (o == null || getClass() != o.getClass())
            {
                return false;
            }
            final PosteDesearlizado that = (PosteDesearlizado) o;
            return Double.compare(that.x, x) == 0 && Double.compare(that.y, y) == 0 && Objects.equals(nombre, that.nombre);
        }

        @Override
        public
        int hashCode()
        {
            return Objects.hash(x, y, nombre);
        }
    }

    @Test
    public  void testCuadrilla_1_ok(){
        ISistema sistema=this.implementacionSistema();
        double [][] postes=registrarGrafoCompleto(sistema,10);
        //Si el grafo es completo debería devvolvernos todo el grafo
        Retorno ret=sistema.cuadrillaAuditoria(postes[0][0],postes[0][1],1);
        assertEquals(Retorno.Resultado.OK,ret.resultado);
        PosteDesearlizado[] postesResultado = deserializarPostes(ret);

        assertListasIguales(postesResultado,postes);
    }
    @Test
    public  void testCuadrilla_2_ok(){
        ISistema sistema=this.implementacionSistema();
        double [][] postes=registrarGrafoCompleto(sistema,10);
        //Si el grafo es completo con 0 maximo tramos, solo deberíamos tener el nodo
        Retorno ret=sistema.cuadrillaAuditoria(postes[0][0],postes[0][1],0);
        assertEquals(Retorno.Resultado.OK,ret.resultado);
        PosteDesearlizado[] postesResultado = deserializarPostes(ret);

        assertListasIguales(postesResultado,new double[][]{postes[0]});
    }


    @Test
    public  void testCuadrilla_3_ok(){
        ISistema sistema=this.implementacionSistema();
        //creamos un grafo completo sin las aristas que van del 0 al punto 9
        boolean[][] ignorar=new boolean[10][10];
        ignorar[0][9]=true;
        ignorar[9][0]=true;
        double [][] postes=registrarGrafoCompleto(sistema,10,ignorar);

        Retorno ret=sistema.cuadrillaAuditoria(postes[0][0],postes[0][1],1);
        assertEquals(Retorno.Resultado.OK,ret.resultado);

        PosteDesearlizado[] postesResultado = deserializarPostes(ret);
assertEquals(9,postesResultado.length);

        ret=sistema.cuadrillaAuditoria(postes[0][0],postes[0][1],2);
        assertEquals(Retorno.Resultado.OK,ret.resultado);
        //Con 2 tramos deberiamos poder acceder a todos
        postesResultado = deserializarPostes(ret);
        assertListasIguales(postesResultado,postes);
    }
    @Test
    public  void testCuadrilla_4_ok(){
        ISistema sistema=this.implementacionSistema();


        int n=7;
        int coordCentro=n/2;
        //Creamos un grafo tipo malla de nxn
        double [][] postes=registrarGrafoLatttice(sistema,n,n,1,1);
        //Desde el centro con 1 paso accedemos a todos los adyacentes
        double [] centro=getPuntoLattice(postes,coordCentro,coordCentro,n);//medio
        double[][] adyacentesConCentro=new double[][]{
            getPuntoLattice(postes,coordCentro,coordCentro,n),
            getPuntoLattice(postes,coordCentro+1,coordCentro,n),
            getPuntoLattice(postes,coordCentro-1,coordCentro,n),
            getPuntoLattice(postes,coordCentro,coordCentro+1,n),
            getPuntoLattice(postes,coordCentro,coordCentro-1,n),
        };
        Retorno ret=sistema.cuadrillaAuditoria(centro[0],centro[1],1);
        assertEquals(Retorno.Resultado.OK,ret.resultado);
        PosteDesearlizado[] postesResultado = deserializarPostes(ret);
        assertListasIguales(postesResultado,adyacentesConCentro);

        double[][] accesiblesDesdeElCentroCon2Pasos=new double[][]{
            //Los adyacentes
            getPuntoLattice(postes,coordCentro,coordCentro,n),
            getPuntoLattice(postes,coordCentro+1,coordCentro,n),
            getPuntoLattice(postes,coordCentro-1,coordCentro,n),
            getPuntoLattice(postes,coordCentro,coordCentro+1,n),
            getPuntoLattice(postes,coordCentro,coordCentro-1,n),
            //Los adyacentes de los adyacentes, es el cuadrado alrededor del centro de 4x4 - los 4 vertices de las puntas
            getPuntoLattice(postes,coordCentro+2,coordCentro,n),
            getPuntoLattice(postes,coordCentro-2,coordCentro,n),
            getPuntoLattice(postes,coordCentro+1,coordCentro+1,n),
            getPuntoLattice(postes,coordCentro+1,coordCentro-1,n),
            getPuntoLattice(postes,coordCentro-1,coordCentro+1,n),
            getPuntoLattice(postes,coordCentro-1,coordCentro-1,n),
            getPuntoLattice(postes,coordCentro,coordCentro+2,n),
            getPuntoLattice(postes,coordCentro,coordCentro-2,n),

        };
        ret=sistema.cuadrillaAuditoria(centro[0],centro[1],2);
        assertEquals(Retorno.Resultado.OK,ret.resultado);
        postesResultado = deserializarPostes(ret);
        assertListasIguales(postesResultado,accesiblesDesdeElCentroCon2Pasos);

        ret=sistema.cuadrillaAuditoria(0,0,2);
        assertEquals(Retorno.Resultado.OK,ret.resultado);
        postesResultado = deserializarPostes(ret);

        double[][] accesiblesDesdeLaPuntaIzquierdaCon2Pasos=new double[][]{
            //Los adyacentes
            getPuntoLattice(postes,0,0,n),
            getPuntoLattice(postes,1,0,n),
            getPuntoLattice(postes,0,1,n),
            //Los adyacentes de los adyacentes
            getPuntoLattice(postes,1,1,n),
            getPuntoLattice(postes,2,0,n),
            getPuntoLattice(postes,0,2,n),

        };
        assertListasIguales(postesResultado,accesiblesDesdeLaPuntaIzquierdaCon2Pasos);

    }

    @Test
    public  void testCaminoMasCorto_1_ok(){
        ISistema sistema=this.implementacionSistema();
        int n=5;
        int coordCentro=n/2;
        double [][] postes=registrarGrafoLatttice(sistema,n,n,1,1);
        double[] inicio=getPuntoLattice(postes,0,0,n);
        double[] fin=getPuntoLattice(postes,n-1,n-1,n);

        Retorno ret=sistema.caminoMinimoEnBuenEstado(inicio[0],inicio[1],fin[0],fin[1]);
        assertEquals(Retorno.Resultado.OK,ret.resultado);
        PosteDesearlizado[] postesResultado = deserializarPostes(ret);

        assertEquals(8,ret.valorEntero); //Hay varios caminos minimos
        assertEquals(9,postesResultado.length); //Hay varios caminos minimos
        assertEquals(posteDesearlizadoLattice(postes,0,0,n),postesResultado[0]);
        assertEquals(posteDesearlizadoLattice(postes,n-1,n-1,n),postesResultado[postesResultado.length-1]);

        //Agregamos una diagonal
        sistema.registrarTramo(getPuntoLattice(postes,2,1,n)[0],
                               getPuntoLattice(postes,2,1,n)[1],
                               getPuntoLattice(postes,3,2,n)[0],
                               getPuntoLattice(postes,3,2,n)[1],0.5);

        ret=sistema.caminoMinimoEnBuenEstado(inicio[0],inicio[1],fin[0],fin[1]);
        assertEquals(Retorno.Resultado.OK,ret.resultado);
        postesResultado = deserializarPostes(ret);
        assertEquals(6,ret.valorEntero); //Se redondea al minimo
        assertEquals(8,postesResultado.length); //Hay varios caminos minimos
        assertEquals(posteDesearlizadoLattice(postes,0,0,n),postesResultado[0]);
        assertEquals(posteDesearlizadoLattice(postes,n-1,n-1,n),postesResultado[postesResultado.length-1]);
        assertTrue(Arrays.asList(postesResultado).contains(posteDesearlizadoLattice(postes,2,1,n)));
        assertTrue(Arrays.asList(postesResultado).contains(posteDesearlizadoLattice(postes,3,2,n)));
    }

    @Test
    public  void testCaminoMasCorto_2_ok(){
        ISistema sistema=this.implementacionSistema();
        //Vamos a dibujar un pentagono, un cuadrado, y un triangulo
        int g1_max=5,g1_inicio=0;
        int g2_max=4,g2_inicio=g1_max;
        int g3_max=3,g3_inicio=g2_inicio+g2_max;
        double[][] postes=new double[g1_max+g2_max+g3_max][2];
        sistema.inicializarSistema(g1_max+g2_max+g3_max);
        dibujarPoligonoRegular(g1_inicio,g1_max,sistema,0,0,5,postes);
        dibujarPoligonoRegular(g2_inicio,g2_max,sistema,6,0,5,postes);
        dibujarPoligonoRegular(g3_inicio,g3_max,sistema,0,7,5,postes);

        //registramos los arcos entre los poligonos
        //punta derecha del pentagono con la punta izquierda del cuadrado
        registrarEntrePostes(sistema,postes,g1_inicio,g2_inicio+2);
        //punta de arriba del pentagono con la punta arriba cuadrado
        registrarEntrePostes(sistema,postes,g1_inicio+1,g2_inicio+1);
        //punta de abajo del pentagono con la punta derecha cuadrado
        registrarEntrePostes(sistema,postes,g1_inicio,g2_inicio+3);

        registrarEntrePostes(sistema,postes,g3_inicio,g2_inicio+1);
        registrarEntrePostes(sistema,postes,g3_inicio,g2_inicio+2);

        registrarEntrePostes(sistema,postes,g1_inicio,g3_inicio+2);
        Retorno ret=sistema.caminoMinimoEnBuenEstado(postes[0][0],postes[0][1],postes[g3_inicio+1][0],postes[g3_inicio+1][1]);
        assertEquals(Retorno.Resultado.OK,ret.resultado);
        PosteDesearlizado[] postesResultado = deserializarPostes(ret);
        PosteDesearlizado[] resultadoEsperado=new PosteDesearlizado[]{
            posteDesearlizado(postes,0),
            posteDesearlizado(postes,11),
            posteDesearlizado(postes,10),
        };
        Assertions.assertArrayEquals(postesResultado, resultadoEsperado);
        assertEquals(10,ret.valorEntero);
        actualizarEstadoCamino(sistema, postes, g1_inicio,g3_inicio+2, ISistema.Estado.MALO);

        ret=sistema.caminoMinimoEnBuenEstado(postes[0][0],postes[0][1],postes[g3_inicio+1][0],postes[g3_inicio+1][1]);
        postesResultado = deserializarPostes(ret);
        resultadoEsperado=new PosteDesearlizado[]{
            posteDesearlizado(postes,0),
            posteDesearlizado(postes,7),
            posteDesearlizado(postes,9),
            posteDesearlizado(postes,10),
        };
        Assertions.assertArrayEquals(resultadoEsperado,postesResultado);
        assertEquals(12,ret.valorEntero);
        actualizarEstadoCamino(sistema, postes, 7,9, ISistema.Estado.MALO);
        actualizarEstadoCamino(sistema, postes, 0,7, ISistema.Estado.REGULAR);//No deberia afectar el resultado

        resultadoEsperado=new PosteDesearlizado[]{
            posteDesearlizado(postes,0),
            posteDesearlizado(postes,7),
            posteDesearlizado(postes,6),
            posteDesearlizado(postes,9),
            posteDesearlizado(postes,10),
        };
        actualizarEstadoCamino(sistema, postes, 0,7, ISistema.Estado.REGULAR);//No deberia afectar el resultado
        ret=sistema.caminoMinimoEnBuenEstado(postes[0][0],postes[0][1],postes[g3_inicio+1][0],postes[g3_inicio+1][1]);
        postesResultado = deserializarPostes(ret);
        Assertions.assertArrayEquals(resultadoEsperado,postesResultado);
        assertEquals(14,ret.valorEntero);
        actualizarEstadoCamino(sistema, postes, 2, 1, ISistema.Estado.REGULAR);//No debe afectar.
        resultadoEsperado=new PosteDesearlizado[]{
            posteDesearlizado(postes,2),
            posteDesearlizado(postes,1),
            posteDesearlizado(postes,6),
            posteDesearlizado(postes,9),
            posteDesearlizado(postes,10),
        };
        ret=sistema.caminoMinimoEnBuenEstado(postes[2][0],postes[2][1],postes[g3_inicio+1][0],postes[g3_inicio+1][1]);
        postesResultado = deserializarPostes(ret);
        System.out.println(Arrays.stream(postesResultado).map(Object::toString).collect(Collectors.toList()));
        assertEquals(18,ret.valorEntero);
        Assertions.assertArrayEquals(resultadoEsperado,postesResultado);

    }

    private
    void actualizarEstadoCamino(final ISistema sistema,
                                final double[][] postes,
                                final int inicio,
                                final int fin,
                                final ISistema.Estado estado)
    {
        assertEquals(Retorno.Resultado.OK,
                     sistema.actualizarTramo(postes[inicio][0],postes[inicio][1],
                                             postes[fin][0],postes[fin][1],
                                             distancia(postes[fin],postes[inicio]),
                                             estado).resultado);
    }

    private
    PosteDesearlizado posteDesearlizado(final double[][] postes,
                           final int idx)
    {
        return new PosteDesearlizado(postes[idx][0],postes[idx][1],"Poste "+idx);
    }

    private
    void registrarEntrePostes(final ISistema sistema,
                              final double[][] postes,
                              final int posteInicio,
                              final int posteFin)
    {
        double [] poste1=postes[posteInicio];
        double [] poste2=postes[posteFin];
        assertEquals(
            Retorno.Resultado.OK,
        sistema.registrarTramo(poste1[0],poste1[1],poste2[0],poste2[1],distancia(poste1,poste2)).resultado);
    }

    private
    void dibujarPoligonoRegular(final int inicioPuntos,
                                final int cantidadPuntos,
                                final ISistema sistema,
                                final double offsetX,
                                final double offsetY,
                                final double diametroPoligono ,
                                final double[][] postes)
    {
        double radioPoligono =diametroPoligono/2;
        double centroX=offsetX+radioPoligono;
        double centroY=offsetY+radioPoligono;

        for(int i=0;i<cantidadPuntos;i++){
            double x=centroX+radioPoligono*Math.cos(i* 2*Math.PI/cantidadPuntos);
            double y=centroY+radioPoligono*Math.sin(i*2*Math.PI/cantidadPuntos);
            postes[inicioPuntos+i][0]=x;
            postes[inicioPuntos+i][1]=y;
            assertEquals(Retorno.Resultado.OK,sistema.registrarPoste(x,y,"Poste "+(i+inicioPuntos)).resultado);
        }
        for(int i=0;i<cantidadPuntos;i++)
        {
            double [] inicio=postes[inicioPuntos+i];
            double [] siguiente=postes[inicioPuntos+ (i+1)%cantidadPuntos];

            assertEquals(Retorno.Resultado.OK,
                         sistema.registrarTramo(inicio[0],inicio[1],
                                                siguiente[0],siguiente[1],
                                                distancia(inicio,siguiente)).resultado);
        }

    }

    private
    PosteDesearlizado posteDesearlizadoLattice(final double[][] postes,
                                 final int fila,
                                 final int col,
                                 final int n)
    {
        return new PosteDesearlizado(postes[fila*n+col][0],postes[fila*n+col][1],"Poste "+(fila*n+col));
    }

    private
    double[][] registrarGrafoLatttice(final ISistema sistema,
                                final int filas,
                                final int columnas,
                                double separacionX,
                                double separacionY)
    {
sistema.inicializarSistema(filas*columnas);
        double [] [] puntos=new double[filas*columnas][2];
        for(int i=0;i<filas;i++){
            for(int j=0;j<columnas;j++){
                double x=j*separacionX;
                double y=i*separacionY;
                puntos[i*columnas+j][0]=x;
                puntos[i*columnas+j][1]=y;
                sistema.registrarPoste(x,y,"Poste "+(i*columnas+j));
            }
        }
        for(int i=0;i<filas;i++)
        {
            for (int j = 0; j < columnas; j++)
            {

                double [] inicio=getPuntoLattice(puntos,i,j,columnas);
                if(j>0)
                {
                    double[] izquierda = getPuntoLattice(puntos, i, j - 1, columnas);
                   assertEquals(Retorno.Resultado.OK,
                                sistema.registrarTramo(inicio[0], inicio[1], izquierda[0], izquierda[1], distancia(inicio, izquierda)).resultado);
                }
                if(i>0){
                    double [] arriba=getPuntoLattice(puntos,i-1,j,columnas);
                    assertEquals(Retorno.Resultado.OK,
                                 sistema.registrarTramo(inicio[0],inicio[1],arriba[0],arriba[1],distancia(inicio,arriba)).resultado);
                }
            }
        }
        return puntos;
    }

    private
    double[] getPuntoLattice(final double[][] puntos,
                         final int fila,
                         final int columna,
                         final int columnas)
    {
        return puntos[fila*columnas+columna];
    }

    private
    PosteDesearlizado[] deserializarPostes(final Retorno ret)
    {
        String [] postesSerializados= ret.valorString.split("\\|");
        PosteDesearlizado[] postesResultado=new PosteDesearlizado[postesSerializados.length];
        for (int i = 0; i < postesSerializados.length; i++)
        {
            String [] posteInfo=postesSerializados[i].split(";");
            postesResultado[i]=new PosteDesearlizado(Double.parseDouble(posteInfo[0]),Double.parseDouble(posteInfo[1]),posteInfo[2]);
        }
        return postesResultado;
    }

    private
    void assertListasIguales(final PosteDesearlizado[] postesResultado,
                             final double[][] postesEsperados)
    {
        assertEquals(postesEsperados.length,postesResultado.length);
        boolean[] visto=new boolean[postesEsperados.length];
        for(int i=0;i<postesEsperados.length;i++){
            boolean existe=false;
            double [] posteABuscar=postesEsperados[i];
            for(int j=0;j<postesEsperados.length;j++){
                PosteDesearlizado p=postesResultado[j];
                if(!visto[j] && posteABuscar[0]==p.x&& posteABuscar[1]==p.y){
                    visto[j]=true;
                    existe=true;
                    break;
                }
            }
            assertTrue(existe,String.format("El poste [%s] (%.2f,%.2f) no esta en el resultado.Resultado: %s",i,postesEsperados[i][0],postesEsperados[i][1],""));
        }
        for (int i = 0; i < visto.length; i++)
        {
            Assertions.assertTrue(visto[i],"Hay elementos que no estaban esperados "+postesResultado[i]);
        }
    }
    private  double[][] registrarGrafoCompleto(ISistema sistema, int cantPuntos){
        return registrarGrafoCompleto(sistema,cantPuntos,null);
    }
    //Es un grafo completo con los vertices alineados en un circulo
    private  double[][] registrarGrafoCompleto(ISistema sistema, int cantPuntos, boolean[][] ignorarAristas){
        sistema.inicializarSistema(cantPuntos);
        double circuloEntero=Math.PI;//Angulos del circulo entero
        double avanceEnRadianes=circuloEntero/cantPuntos;
        double radioCirculo=1;//metros
        double[][] puntos=new double[cantPuntos][2];
        for(int i=0;i<cantPuntos;i++){
            double x=radioCirculo * Math.sin(avanceEnRadianes*i);
            double y=radioCirculo *  Math.cos(avanceEnRadianes*i);

            sistema.registrarPoste(x,y,"Poste "+i);
            puntos[i][0]=x;
            puntos[i][1]=y;
        }
        for(int i=0;i<cantPuntos;i++){
            for(int j=i+1;j<cantPuntos;j++){
                if(ignorarAristas==null||!ignorarAristas[i][j]){
                    sistema.registrarTramo(puntos[i][0],puntos[i][1],puntos[j][0],puntos[j][1],distancia(puntos[i],puntos[j]));
                }

            }
        }
        return puntos;
    }

    private
    double distancia(final double[] puntoA,
                   final double[] puntoB)
    {
        return Math.sqrt(Math.pow(puntoA[0]-puntoB[0],2)+Math.pow(puntoA[1]-puntoB[1],2));
    }


    private
    String serializarObrero(final String cedula,
                          final String nombre)
    {
        return String.format("%s;%s",cedula,nombre);
    }
}








//import static org.junit.jupiter.api.Assertions.*;
//
//import org.junit.jupiter.api.Test;
//
//import uy.edu.ort.obligatorio.ArbolObreros;
//import uy.edu.ort.obligatorio.Retorno;
//import uy.edu.ort.obligatorio.Retorno.Resultado;
//import uy.edu.ort.obligatorio.Sistema;
//
//public class TestObligatorio {
//
//	@Test
//	public void test() {
//		
//		Sistema s = new Sistema();
//		s.inicializarSistema(3);
//		
//		s.registrarObrero("0.000.000-3", "Carlitos");
//		s.registrarObrero("0.000.000-1", "Armando");
//		s.registrarObrero("0.000.000-2", "Santiago");
//		s.registrarObrero("0.000.000-5", "Marta");
//		s.registrarObrero("0.000.000-4", "Raul");
//		s.registrarObrero("0.000.000-0", "`");
//		
//		s.listarObreros();
//		
//		
////		Retorno r1 = s.buscarObrero("2.123.432-2");
////		System.out.println(r1.valorEntero);
////		assertEquals(Resultado.OK, r1.resultado);
//	}
//	
//	
//
//}
