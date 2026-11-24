package com.escola.horarios.usuario;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {
    private final UsuarioArquivo usuarios;

    public LoginController(UsuarioArquivo usuarios) { this.usuarios = usuarios; }

    @GetMapping("/login") public String login() { return "login"; }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String senha, HttpSession s, Model model) {
        if (!usuarios.login(email, senha)) {
            model.addAttribute("erro", "Email ou senha invalidos");
            return "login";
        }
        s.setAttribute("email", email.toLowerCase());
        return "redirect:/";
    }

    @GetMapping("/cadastro") public String cadastro() { return "cadastro"; }

    @PostMapping("/cadastro")
    public String cadastro(@RequestParam String email, @RequestParam String senha,
                           @RequestParam String confirma, Model model) {
        if (usuarios.existe(email)) model.addAttribute("erro", "Email ja cadastrado");
        else if (!senha.equals(confirma)) model.addAttribute("erro", "As senhas nao conferem");
        else {
            usuarios.salvarSenha(email, senha);
            model.addAttribute("msg", "Cadastro feito! Faca login.");
            return "login";
        }
        return "cadastro";
    }

    @GetMapping("/resetar") public String resetar() { return "resetar"; }

    @PostMapping("/resetar")
    public String resetar(@RequestParam String email, @RequestParam String senha,
                          @RequestParam String confirma, Model model) {
        if (!usuarios.existe(email)) model.addAttribute("erro", "Email nao cadastrado");
        else if (!senha.equals(confirma)) model.addAttribute("erro", "As senhas nao conferem");
        else {
            usuarios.salvarSenha(email, senha);
            model.addAttribute("msg", "Senha alterada! Faca login.");
            return "login";
        }
        return "resetar";
    }

    @GetMapping("/logout")
    public String logout(HttpSession s) {
        s.invalidate();
        return "redirect:/login";
    }
}
