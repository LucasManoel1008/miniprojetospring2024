package br.com.itb.miniprojetospring.controller;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.itb.miniprojetospring.model.Empresa;
import br.com.itb.miniprojetospring.model.Servico;
import br.com.itb.miniprojetospring.service.EmpresaService;
import br.com.itb.miniprojetospring.service.ServicoService;

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
            Servico servicoSalvo = servicoService.save(servico);
            System.out.println("Serviço salvo com sucesso!");
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

    // Filtra serviços por status == true e retorna quando foram públicados
    @GetMapping("/listar-servicos")
    public ResponseEntity<List<Servico>> listarServicosPorDisponibilidade() {
        LocalDateTime dataAtual = LocalDateTime.now();

        List<Servico> listarServicos = servicoService.findAll().stream()
                .map(servico -> {
                    if (servico.getDisponibilidade_servico() != null) {
                        LocalDateTime dataDisponibilidade = servico.getDisponibilidade_servico().toInstant().atZone(ZoneId.of("America/Sao_Paulo")).toLocalDateTime();
                        servico.setTempo_servico(servicoService.calcularTempoPassado(dataDisponibilidade, dataAtual));
                    }
                    return servico;
                    
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(listarServicos);
    }

    // Filtra serviços pelo filtro na tela "Servicos"
    @GetMapping("/filtrar")
    public ResponseEntity<List<Servico>> filtrarServicos(@RequestParam String categoria, @RequestParam Double valor, @RequestParam String data) {
        List<Servico> filtrarServico = servicoService.findAll().stream()
                .filter(servico -> categoria.equals("todas") || servico.getCategoria_servico().equalsIgnoreCase(categoria))
                .filter(servico -> valor == 0 || Double.parseDouble(servico.getValor_estimado_servico()) < valor)
                .map(this::setTempoServico)
                .collect(Collectors.toList());
       List<Servico> servicosFormatados = new ArrayList<>(servicoService.ordenarServico(filtrarServico, data));
        System.out.println("serviços organizada: " + Arrays.toString(servicosFormatados.toArray()));
        return ResponseEntity.ok(servicosFormatados);
    }

    private Servico setTempoServico(Servico servico) {
        Instant data = Instant.now();
        ZoneId zoneId = ZoneId.of("America/Sao_Paulo");
        LocalDateTime dataAtual = LocalDateTime.ofInstant(data, zoneId);
        LocalDateTime dataDisponibilidade = servico.getDisponibilidade_servico().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        servico.setTempo_servico(servicoService.calcularTempoPassado(dataDisponibilidade, dataAtual));
        return servico;
    }

    @GetMapping("/pesquisar-servico")
    public ResponseEntity<List<Servico>> pesquisarServico(@RequestParam String nome) {
        if (nome.equals("") || nome == null) {
            return ResponseEntity.ok(listarServicosPorDisponibilidade().getBody());
        }
        List<Servico> servicos = servicoService.findAll().stream()
                .filter(servico -> servico.getNome_servico().toLowerCase().contains(nome.toLowerCase()))
                .map(servico -> {
                    LocalDateTime dataAtual = LocalDateTime.now();
                    if (servico.getDisponibilidade_servico() != null) {
                        LocalDateTime dataDisponibilidade = servico.getDisponibilidade_servico()
                                .toInstant()
                                .atZone(ZoneId.systemDefault())
                                .toLocalDateTime();
                        servico.setTempo_servico(servicoService.calcularTempoPassado(dataDisponibilidade, dataAtual));
                    }
                    return servico;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(servicos);
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
