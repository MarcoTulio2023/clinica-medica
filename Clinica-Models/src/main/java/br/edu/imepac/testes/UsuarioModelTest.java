package br.edu.imepac.testes;

import br.edu.imepac.models.UsuarioModel;
import org.junit.jupiter.api.Test;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

public class UsuarioModelTest {

    @Test
    public void testEntityAnnotation() {
        // Check if the class is annotated with @Entity
        assertTrue(UsuarioModel.class.isAnnotationPresent(Entity.class), "UsuarioModel should be annotated with @Entity");
    }

    @Test
    public void testTableAnnotation() {
        // Check if the class is annotated with @Table and has the correct table name
        Table tableAnnotation = UsuarioModel.class.getAnnotation(Table.class);
        assertNotNull(tableAnnotation, "UsuarioModel should be annotated with @Table");
        assertEquals("usuarios", tableAnnotation.name(), "Table name should be 'usuarios'");
    }

    @Test
    public void testIdFieldAnnotation() throws NoSuchFieldException {
        // Check if the id_usuario field is annotated with @Id and @GeneratedValue
        Field idField = UsuarioModel.class.getDeclaredField("id_usuario");
        assertTrue(idField.isAnnotationPresent(Id.class), "id_usuario should be annotated with @Id");
        GeneratedValue generatedValueAnnotation = idField.getAnnotation(GeneratedValue.class);
        assertNotNull(generatedValueAnnotation, "id_usuario should be annotated with @GeneratedValue");
        assertEquals(GenerationType.IDENTITY, generatedValueAnnotation.strategy(), "Generation strategy should be IDENTITY");
    }

    @Test
    public void testNomeField() throws NoSuchFieldException {
        // Check if the nome field exists
        Field nomeField = UsuarioModel.class.getDeclaredField("nome");
        assertNotNull(nomeField, "nome field should exist in UsuarioModel");
    }

    @Test
    public void testSenhaField() throws NoSuchFieldException {
        // Check if the senha field exists
        Field senhaField = UsuarioModel.class.getDeclaredField("senha");
        assertNotNull(senhaField, "senha field should exist in UsuarioModel");
    }

    @Test
    public void testGettersAndSetters() {
        // Create an instance of UsuarioModel
        UsuarioModel usuario = new UsuarioModel();

        // Set values
        usuario.setId_usuario(1L);
        usuario.setNome("John Doe");
        usuario.setSenha("password123");

        // Test getters
        assertEquals(Long.valueOf(1L), usuario.getId_usuario(), "id_usuario should be 1");
        assertEquals("John Doe", usuario.getNome(), "nome should be 'John Doe'");
        assertEquals("password123", usuario.getSenha(), "senha should be 'password123'");
    }
}
