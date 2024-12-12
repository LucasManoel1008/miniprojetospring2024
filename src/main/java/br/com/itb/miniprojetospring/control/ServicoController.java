package br.com.itb.miniprojetospring.control;

import br.com.itb.miniprojetospring.model.Empresa;
import br.com.itb.miniprojetospring.model.Servico;
import br.com.itb.miniprojetospring.service.EmpresaService;
import br.com.itb.miniprojetospring.service.ServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600, allowCredentials = "false")
@RequestMapping("/servico")
public class ServicoController {
    @Autowired
    private final ServicoService servicoService;

    @Autowired
    private final EmpresaService empresaService;

    public ServicoController(ServicoService servicoService, EmpresaService empresaService) {
        this.servicoService = servicoService;
        this.empresaService = empresaService;
    }


    @PostMapping
    public ResponseEntity<Servico> createServico(@RequestBody Servico servico, @RequestParam String cnpjEmpresa) {
        Optional<Empresa> empresaOptional = empresaService.findAllById(cnpjEmpresa);
        if (empresaOptional.isPresent()) {
            Empresa empresa = empresaOptional.get();
            servico.setEmpresa(empresa);

            // Log para verificar a empresa associada
            System.out.println("Empresa associada ao serviço: " + empresa);

            Servico servicoSalvo = servicoService.save(servico);

            // Log para verificar o serviço salvo
            System.out.println("Serviço salvo: " + servicoSalvo);
            return ResponseEntity.ok(servicoSalvo);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Servico> getServicoById(@PathVariable Long id) {
        Optional<Servico> servicoOptional = servicoService.findById(id);
        if (servicoOptional.isPresent()) {
            return ResponseEntity.ok(servicoOptional.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Servico>> getAllServicos() {
        return ResponseEntity.ok(servicoService.findAll());
    }
    @GetMapping("/empresa/{cnpj}")
    public ResponseEntity<List<Servico>> getServicosByEmpresa(@PathVariable String cnpj) {
        Optional<Empresa> empresaOptional = empresaService.findAllById(cnpj);
        if (empresaOptional.isPresent()) {
            Empresa empresa = empresaOptional.get();
            List<Servico> servicos = servicoService.findByEmpresa(empresa);
            return new ResponseEntity<>(servicos, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateServico(@PathVariable Long id, @RequestBody Servico servico) {
        Optional<Servico> servicoOptional = servicoService.findById(id);
        if (servicoOptional.isPresent()) {
            Servico existingServico = servicoOptional.get();
            existingServico.setNome_servico(servico.getNome_servico());
            existingServico.setDescricao_servico(servico.getDescricao_servico());
            existingServico.setCategoria_servico(servico.getCategoria_servico());
            existingServico.setCriterios_servico(servico.getCriterios_servico());
            existingServico.setStatus_servico(servico.getStatus_servico());
            existingServico.setDisponibilidade_servico(servico.getDisponibilidade_servico());
            existingServico.setLocal_servico(servico.getLocal_servico());
            existingServico.setValor_estimado_servico(servico.getValor_estimado_servico());
            servicoService.update(existingServico);
            return ResponseEntity.ok(existingServico);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteServico(@PathVariable Long id) {
        servicoService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @DeleteMapping
    public ResponseEntity<Void> deleteServicosByEmpresa(@RequestParam String cnpjEmpresa) {
        Optional<Empresa> empresaOptional = empresaService.findAllById(cnpjEmpresa);
        if (empresaOptional.isPresent()) {
            Empresa empresa = empresaOptional.get();
            List<Servico> servicos = servicoService.findAllByEmpresa(empresa);
            for (Servico servico : servicos) {
                servicoService.delete(servico.getId());
            }
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
