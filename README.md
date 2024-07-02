CONTROLLER ----------------------------

1. Anotações Spring Boot
@RestController: Indica que a classe é um controlador onde cada método retorna um objeto de domínio em vez de uma visão. É uma combinação de @Controller e @ResponseBody.
@RequestMapping("medico"): Define a URL base para todos os endpoints dentro desta classe. Neste caso, todos os endpoints começarão com /medico.
@Autowired: Injeta automaticamente a dependência do MedicoService na classe MedicoController.
2. Métodos HTTP
@PostMapping: Mapeia requisições HTTP POST para o método saveDoctor. Usado para criar um novo recurso.
@GetMapping: Mapeia requisições HTTP GET para os métodos listAllDoctors e getDoctorById. Usado para recuperar recursos.
@PutMapping: Mapeia requisições HTTP PUT para o método updateDoctor. Usado para atualizar um recurso existente.
@DeleteMapping: Mapeia requisições HTTP DELETE para o método deleteDoctor. Usado para deletar um recurso.
3. ResponseEntity
ResponseEntity: Representa toda a resposta HTTP: código de status, cabeçalhos e corpo. É usado para personalizar a resposta HTTP.
4. Status HTTP
HttpStatus.CREATED: Indica que a requisição foi bem-sucedida e um novo recurso foi criado.
HttpStatus.OK: Indica que a requisição foi bem-sucedida.
HttpStatus.NOT_FOUND: Indica que o recurso solicitado não foi encontrado.
5. DTOs (Data Transfer Objects)
MedicoCreateRequest: Um objeto que encapsula os dados necessários para criar um novo médico.
MedicoDto: Um objeto que encapsula os dados do médico que serão retornados nas respostas.
6. Serviço
MedicoService: Uma classe de serviço que contém a lógica de negócios para gerenciar médicos. É injetada no controlador usando @Autowired.
7. Métodos do Controlador
saveDoctor: Recebe um MedicoCreateRequest no corpo da requisição, chama o serviço para salvar o médico e retorna o médico salvo com o status HTTP 201 (CREATED).
listAllDoctors: Chama o serviço para recuperar todos os médicos e retorna a lista com o status HTTP 200 (OK).
getDoctorById: Recebe um ID como parâmetro de caminho, chama o serviço para recuperar o médico correspondente e retorna o médico com o status HTTP 200 (OK) ou 404 (NOT FOUND) se não for encontrado.
updateDoctor: Recebe um ID como parâmetro de caminho e um MedicoDto no corpo da requisição, chama o serviço para atualizar o médico e retorna o médico atualizado com o status HTTP 200 (OK) ou 404 (NOT FOUND) se não for encontrado.
deleteDoctor: Recebe um ID como parâmetro de caminho, chama o serviço para deletar o médico e retorna o status HTTP 200 (OK).
Exemplo de Código

@RestController
@RequestMapping("medico")
public class MedicoController {

    @Autowired
    private MedicoService medicoService;

    @PostMapping
    public ResponseEntity<MedicoDto> saveDoctor(@RequestBody MedicoCreateRequest medicoCreateRequest) {
        MedicoDto savedMedico = medicoService.save(medicoCreateRequest);
        return new ResponseEntity<>(savedMedico, HttpStatus.CREATED);
    }

    @GetMappinghttps://codeshare.io/back
    public ResponseEntity<List<MedicoDto>> listAllDoctors() {
        List<MedicoDto> medicos = medicoService.findAll();
        return new ResponseEntity<>(medicos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicoDto> getDoctorById(@PathVariable Long id) {
        MedicoDto medicoDto = medicoService.findById(id);.io/
        if (medicoDto != null) {
            return new ResponseEntity<>(medicoDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicoDto> updateDoctor(@PathVariable Long id, @RequestBody MedicoDto medicoDetails) {
        MedicoDto updatedMedico = medicoService.update(id, medicoDetails);
        if (updatedMedico != null) {
            return new ResponseEntity<>(updatedMedico, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteDoctor(@PathVariable Long id) {
        medicoService.delete(id);
    }
}


SERVICES -------------------------------

1. Anotações Spring Boot
@Service: Indica que a classe é um serviço, um componente do Spring que contém a lógica de negócios. É um estereótipo especializado de @Component.
2. Injeção de Dependência
@Autowired: Injeta automaticamente a dependência do MedicoRepository na classe MedicoService. Isso permite que o Spring resolva e injete o bean MedicoRepository necessário.
3. Repositório
MedicoRepository: Um repositório que estende JpaRepository ou CrudRepository, fornecendo métodos para operações CRUD (Create, Read, Update, Delete) no banco de dados.
4. Optional
Optional: Uma classe contêiner que pode ou não conter um valor não nulo. É usada para evitar NullPointerException e para expressar claramente que um valor pode estar ausente.
5. DTOs (Data Transfer Objects)
MedicoCreateRequest: Um objeto que encapsula os dados necessários para criar um novo médico.
MedicoDto: Um objeto que encapsula os dados do médico que serão retornados nas respostas.
6. Modelos
MedicoModel: Uma entidade JPA que representa a tabela medicos no banco de dados.
7. Métodos do Serviço
delete: Recebe um ID e chama o repositório para deletar o médico correspondente.
findAll: Chama o repositório para recuperar todos os médicos e os converte em uma lista de MedicoDto.
update: Recebe um ID e um MedicoDto, chama o repositório para atualizar o médico correspondente e retorna o médico atualizado como MedicoDto.
save: Recebe um MedicoCreateRequest, cria um novo MedicoModel, salva no repositório e retorna o médico salvo como MedicoDto.
findById: Recebe um ID, chama o repositório para recuperar o médico correspondente e retorna o médico como MedicoDto.
Exemplo de Código


@Service
public class MedicoService {

    @Autowired
    private MedicoRepository medicoRepository;

    public void delete(Long id) {
        medicoRepository.deleteById(id);
    }

    public List<MedicoDto> findAll() {
        List<MedicoModel> medicos = medicoRepository.findAll();
        return medicos.stream().map(medico -> {
            MedicoDto medicoDto = new MedicoDto();
            medicoDto.setId(medico.getId());
            medicoDto.setNome(medico.getNome());
            medicoDto.setCrm(medico.getCrm());
            return medicoDto;
        }).collect(Collectors.toList());
    }

    public MedicoDto update(Long id, MedicoDto medicoDetails) {
        Optional<MedicoModel> optionalMedico = medicoRepository.findById(id);
        if (optionalMedico.isPresent()) {
            MedicoModel medicoModel = optionalMedico.get();
            medicoModel.setNome(medicoDetails.getNome());
            medicoModel.setCrm(medicoDetails.getCrm());
            MedicoModel updatedMedico = medicoRepository.save(medicoModel);
            MedicoDto medicoDto = new MedicoDto();
            medicoDto.setId(updatedMedico.getId());
            medicoDto.setNome(updatedMedico.getNome());
            medicoDto.setCrm(updatedMedico.getCrm());
            return medicoDto;
        } else {
            return null;
        }
    }

    public MedicoDto save(MedicoCreateRequest medicoRequest) {
        MedicoModel medicoModel = new MedicoModel();
        medicoModel.setNome(medicoRequest.getNome());
        medicoModel.setCrm(medicoRequest.getCrm());
        medicoModel.setSenha(medicoRequest.getSenha());
        MedicoModel savedMedico = medicoRepository.save(medicoModel);
        MedicoDto medicoDto = new MedicoDto();
        medicoDto.setId(savedMedico.getId());
        medicoDto.setNome(savedMedico.getNome());
        medicoDto.setCrm(savedMedico.getCrm());
        return medicoDto;
    }

    public MedicoDto findById(Long id) {
        Optional<MedicoModel> optionalMedico = medicoRepository.findById(id);
        if (optionalMedico.isPresent()) {
            MedicoModel medicoModel = optionalMedico.get();
            MedicoDto medicoDto = new MedicoDto();
            medicoDto.setId(medicoModel.getId());
            medicoDto.setNome(medicoModel.getNome());
            medicoDto.setCrm(medicoModel.getCrm());
            return medicoDto;
        } else {
            return null;
        }
    }
}


MODEL -----------------------------------------
1. Anotações JPA (Java Persistence API)
@Entity: Indica que a classe é uma entidade JPA. Uma entidade representa uma tabela no banco de dados, e cada instância dessa classe representa uma linha na tabela.
@Table(name = "medicos"): Especifica o nome da tabela no banco de dados que esta entidade mapeia. Neste caso, a tabela se chama medicos.
@Id: Indica o campo que é a chave primária da entidade.
@GeneratedValue(strategy = GenerationType.IDENTITY): Especifica que o valor da chave primária será gerado automaticamente pelo banco de dados. A estratégia GenerationType.IDENTITY indica que o banco de dados gerará um valor único para a chave primária.
2. Lombok
@Data: É uma anotação do Lombok que gera automaticamente getters, setters, toString(), equals(), hashCode(), e um construtor para todos os campos finais. Isso reduz a quantidade de código boilerplate que você precisa escrever.
3. Campos da Classe
private Long id: Representa a chave primária da entidade. É um identificador único para cada médico.
private String nome: Representa o nome do médico.
private String crm: Representa o número do CRM (Conselho Regional de Medicina) do médico.
private String senha: Representa a senha do médico. Este campo deve ser tratado com cuidado para garantir a segurança.
Exemplo de Código



package br.edu.imepac.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "medicos")
@Data
public class MedicoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String crm;
    private String senha;
}

Explicação Detalhada
@Entity: Esta anotação informa ao JPA que a classe MedicoModel deve ser mapeada para uma tabela no banco de dados.
@Table(name = "medicos"): Define o nome da tabela no banco de dados como medicos. Se esta anotação não fosse fornecida, o JPA usaria o nome da classe como o nome da tabela por padrão.
@Id: Marca o campo id como a chave primária da entidade.
@GeneratedValue(strategy = GenerationType.IDENTITY): Configura a geração automática do valor do campo id pelo banco de dados, usando a estratégia de identidade.
@Data: Lombok gera automaticamente os métodos getter e setter para todos os campos, além de toString(), equals(), hashCode(), e um construtor padrão.
Campos:
id: Um identificador único para cada instância de MedicoModel.
nome: O nome do médico.
crm: O número do CRM do médico.
senha: A senha do médico, que deve ser armazenada de forma segura (por exemplo, usando hashing).


REPOSITORY ---------------------------------

Claro! Vamos analisar a interface MedicoRepository e explicar os conceitos utilizados. A interface MedicoRepository é um repositório Spring Data JPA que fornece métodos para realizar operações CRUD (Create, Read, Update, Delete) no banco de dados. Aqui estão os conceitos principais:

1. Anotações Spring Data JPA
@Repository: Indica que a interface é um repositório, um mecanismo de encapsulamento de armazenamento, recuperação e pesquisa de dados. É um estereótipo especializado de @Component.
2. Extensão de Interface
JpaRepository<MedicoModel, Long>: Extende a interface JpaRepository, que é uma especificação do Spring Data JPA para repositórios. JpaRepository fornece métodos CRUD prontos para uso e métodos de paginação e ordenação.
MedicoModel: A entidade que o repositório gerencia.
Long: O tipo da chave primária da entidade MedicoModel.
Exemplo de Código


package br.edu.imepac.repositories;

import br.edu.imepac.models.MedicoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicoRepository extends JpaRepository<MedicoModel, Long> {
}


Explicação Detalhada
@Repository: Esta anotação informa ao Spring que a interface MedicoRepository é um repositório. O Spring trata as classes anotadas com @Repository como beans e as gerencia como parte do contexto da aplicação. Além disso, @Repository pode capturar exceções específicas de persistência e reembalá-las em exceções de tempo de execução não verificadas.

JpaRepository<MedicoModel, Long>:

MedicoModel: A entidade que este repositório gerencia. Cada instância de MedicoModel representa uma linha na tabela medicos no banco de dados.
Long: O tipo da chave primária da entidade MedicoModel. Isso significa que o identificador único de cada MedicoModel é do tipo Long.
Métodos Herdados de JpaRepository
Ao estender JpaRepository, MedicoRepository herda vários métodos para realizar operações CRUD, como:

save(S entity): Salva uma entidade.
findById(ID id): Recupera uma entidade pelo seu ID.
findAll(): Recupera todas as entidades.
deleteById(ID id): Deleta uma entidade pelo seu ID.
count(): Conta o número de entidades.
Esses métodos são implementados automaticamente pelo Spring Data JPA, então você não precisa escrevê-los manualmente.
  
  
  
  
