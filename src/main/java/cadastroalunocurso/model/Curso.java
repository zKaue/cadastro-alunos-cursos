package cadastroalunocurso.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TB_Curso")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_curso")
    private int id;

    @Column (name = "nome", nullable = false, unique = true, length = 50)
    private String nome;

    @OneToMany(mappedBy = "curso", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Matricula> matriculas = new ArrayList<>();

    //Construtores

    public Curso() {}

    public Curso(String nome) {
        this.nome = nome;
    }

    //Getter e Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
