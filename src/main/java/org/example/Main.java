package org.example;

import org.example.dao.*;
import org.example.model.*;

import java.sql.SQLOutput;
import java.time.LocalDate;
import java.util.*;

public class Main {
    static Scanner SC = new Scanner(System.in);

    public static void main(String[] args) {
        menu();
    }

    private static void menu() {
        boolean sair = false;

        System.out.println("""
                \n----- Sistema Controle Industrial -----\n
                1 - Cadastrar Setor
                2 - Cadastrar Máquina
                3 - Cadastrar Produto
                4 - Cadastrar Matéria-Prima
                5 - Criar Ordem de Produção
                6 - Associar Matérias-Primas à Ordem
                7 - Executar Produção
                8 - Consultar Ordens e Estoque
                \n0 - Sair
                \nEscolha uma operação do sistema:""");
        int opcao = SC.nextInt();

        SC.nextLine();

        switch (opcao){
            case 1: {
                cadastrarSetor();
                break;
            }
            case 2: {
                cadastrarMaquina();
                break;
            }
            case 3: {
                cadastrarProduto();
                break;
            }
            case 4: {
                cadastrarMateriaPrima();
                break;
            }
            case 5: {
                criarOrdemProducao();
                break;
            }
            case 6: {
                associarMateriasPrimasOrdem();
                break;
            }
            case 7: {
                executarProducao();
                break;
            }
            case 8: {
                consultaOrdem();
                break;
            }
            case 0: {
                sair = true;
            }
        }
        if (!sair){
            menu();
        }
    }

    private static void consultaOrdem() {
        boolean sair = false;

        while (!sair){
            System.out.println("\n------ CONSULTAS ------" +
                    "\n1 - Ordem de Produção" +
                    "\n2 - Estoque atual de materia prima");
            int opcao = SC.nextInt();

            switch (opcao){
                case 1:{
                    OrdemProducaoDAO ordemProducaoDao = new OrdemProducaoDAO();
                    List<OrdemProducao> ordemProducaoList = ordemProducaoDao.listarOrdem();
                    for (OrdemProducao ordemProducao : ordemProducaoList){
                        System.out.println("\n------- Ordem de Produção -------" +
                                "\nID Ordem: " + ordemProducao.getId() +
                                "\nID Produto: " + ordemProducao.getIdProduto() +
                                "\nID Máquina: " + ordemProducao.getIdMaquina() +
                                "\nQuantidade Produzir: " + ordemProducao.getQuantidadeProduzir() +
                                "\nData solicitação: " + ordemProducao.getDataSolicitacao() +
                                "\nStatus: " + ordemProducao.getStatus());
                    }
                }
            }
        }
    }

    private static void executarProducao() {
        MaquinaDAO maquinaDao = new MaquinaDAO();
        OrdemProducaoDAO ordemProducaoDao = new OrdemProducaoDAO();
        List<Integer> opcaoOrdem = new ArrayList<>();
        List<OrdemProducao> ordemProducaoList = ordemProducaoDao.listarOrdemPendente();

        for (OrdemProducao ordemProducao : ordemProducaoList){
            System.out.println("\n------- Ordem de Produção -------" +
                    "\nID Ordem: " + ordemProducao.getId() +
                    "\nID Produto: " + ordemProducao.getIdProduto() +
                    "\nID Máquina: " + ordemProducao.getIdMaquina() +
                    "\nQuantidade Produzir: " + ordemProducao.getQuantidadeProduzir() +
                    "\nData solicitação: " + ordemProducao.getDataSolicitacao() +
                    "\nStatus: " + ordemProducao.getStatus());
            opcaoOrdem.add(ordemProducao.getId());
        }
        System.out.println("\nDigite o ID da ordem de produção que deseja executar: ");
        int idOrdem = SC.nextInt();

        Map<Integer, Double> atualizacoes = new HashMap<>();

        MateriaPrimaDAO materiaPrimaDao = new MateriaPrimaDAO();
        OrdemMateriaPrimaDAO ordemMateriaPrimaDao = new OrdemMateriaPrimaDAO();

        if (opcaoOrdem.contains(idOrdem)){
            List<OrdemMateriaPrima> ordemMateriaPrimaList = ordemMateriaPrimaDao.buscarOrdemMateriaPorId(idOrdem);

            for (OrdemMateriaPrima ordemMateriaPrima : ordemMateriaPrimaList){
                double estoque = materiaPrimaDao.verificarEstoqueMateriaPorId(ordemMateriaPrima.getIdMateriaPrima());
                if (estoque >= ordemMateriaPrima.getQuantidade()){
                    atualizacoes.put(ordemMateriaPrima.getIdMateriaPrima(), (estoque - ordemMateriaPrima.getQuantidade()));
                } else {
                    System.out.println("Estoque insuficiente para realizar produção.");
                    executarProducao();
                }
            }
            // aqui vou interar a lista do Map atualizações e dar entrada de dados que coletei no forEach anterior e
            // atualizar o estoque atravez do metodo UPDATE no DAO
            for (Map.Entry<Integer, Double> executar : atualizacoes.entrySet()){
                materiaPrimaDao.atualizarEstoque(executar.getKey(), executar.getValue());
            }

            // atualizar ordem de produção para concluida
            ordemProducaoDao.atualizarStatusConcluida(idOrdem, "CONCLUIDA");

            int idMaquina = 0;

            for (OrdemProducao ordemProducao : ordemProducaoList){
                if (ordemProducao.getId() == idOrdem){
                    idMaquina = ordemProducao.getIdMaquina();
                }
            }
            maquinaDao.atualizarStatusOperacional(idMaquina, "OPERACIONAL");
        } else {
            System.out.println("Opção inválida!");
            executarProducao();
        }
    }

    private static void associarMateriasPrimasOrdem() {
        OrdemProducaoDAO ordemProducaoDao = new OrdemProducaoDAO();
        List<Integer> opcaoOrdem = new ArrayList<>();
        List<OrdemProducao> ordemProducaoList = ordemProducaoDao.listarOrdemPendente();

        for (OrdemProducao ordemProducao : ordemProducaoList){
            System.out.println("------- Ordem de Produção -------" +
                    "\nID: " + ordemProducao.getId() +
                    "\nID Produto: " + ordemProducao.getIdProduto() +
                    "\nID Máquina: " + ordemProducao.getIdMaquina() +
                    "\nQuantidade Produzir: " + ordemProducao.getQuantidadeProduzir() +
                    "\nData solicitação: " + ordemProducao.getDataSolicitacao() +
                    "\nStatus: " + ordemProducao.getStatus());
            opcaoOrdem.add(ordemProducao.getId());
        }
        System.out.println("\nDigite o ID da ordem de produção que deseja: ");
        int idOrdem = SC.nextInt();

        if (opcaoOrdem.contains(idOrdem)){
            List<Integer> opcaoMateria = new ArrayList<>();
            MateriaPrimaDAO materiaPrimaDao = new MateriaPrimaDAO();
            List<MateriaPrima> materiaPrimaList = materiaPrimaDao.listarMateriaPrima();

            for(MateriaPrima materiaPrima : materiaPrimaList){
                System.out.println("------ Matéria Prima ------" +
                        "\nID: " + materiaPrima.getId() +
                        "\nNome: " + materiaPrima.getNome() +
                        "\nEstoque: " + materiaPrima.getEstoque());
                opcaoMateria.add(materiaPrima.getId());
            }
            System.out.println("\nDigite o ID da matéria prima que deseja: ");
            int idMateria = SC.nextInt();

            OrdemMateriaPrimaDAO ordemMateriaPrimaDao = new OrdemMateriaPrimaDAO();
            boolean ordemMateriaExiste = ordemMateriaPrimaDao.buscarExistencia(idOrdem, idMateria);

            if (opcaoMateria.contains(idMateria) && !ordemMateriaExiste){
                System.out.println("\nDigite a quantidade da matéria prima que será utilizada");
                double quantidade = SC.nextDouble();
                SC.nextLine();

                double quantidadeEstoque = 0;
                for (MateriaPrima materiaPrima : materiaPrimaList){
                    if (idMateria == materiaPrima.getId()){
                        quantidadeEstoque = materiaPrima.getEstoque();
                    }
                }

                if (quantidadeEstoque >= quantidade && quantidade > 0){
                    OrdemMateriaPrima ordemMateriaPrima = new OrdemMateriaPrima(idOrdem, idMateria, quantidade);
                    ordemMateriaPrimaDao.inserirOrdemMateria(ordemMateriaPrima);
                } else {
                    System.out.println("Quantidade inválida. Insira um número possível!");
                }
            } else {
                System.out.println("Opção inválida! Já existe essa ordem cadastrada ou não possui ID da matéria prima.");
            }
        } else {
            System.out.println("Não existe ID Digitado! Digite outro ID.");
        }
    }

    private static void criarOrdemProducao() {
        ProdutoDAO produtoDao = new ProdutoDAO();
        List<Integer> opcaoProduto = new ArrayList<>();
        List<Produto> produtosLista = produtoDao.listarProdutos();

        for (Produto produto : produtosLista){
            System.out.println("-------- Produto --------" +
                    "\nID: " + produto.getId() +
                    "\nNOME: " + produto.getNome() +
                    "\nCATEGORIA: " + produto.getCategoria() +
                    "\n---------------------\n");
            opcaoProduto.add(produto.getId());
        }
        System.out.println("Digite o ID do produto que deseja: ");
        int idProduto = SC.nextInt();

        if (opcaoProduto.contains(idProduto)){

            List<Integer> opcoesMaquina = new ArrayList<>();
            MaquinaDAO maquinaDao = new MaquinaDAO();
            List<Maquina> maquinas = maquinaDao.listarMaquinaOperacional();

            for (Maquina maquina : maquinas){
                System.out.println("------ Máquinas ------" +
                        "\nID: " + maquina.getId() +
                        "\nNOME: " + maquina.getNome() +
                        "n\nID Setor: " + maquina.getIdSetor() +
                        "\nSTATUS: " + maquina.getStatus() +
                        "\n------------------\n");
                opcoesMaquina.add(maquina.getId());
            }
            System.out.println("Digite o ID da máquina que deseja: ");
            int idMaquina = SC.nextInt();

            if (opcoesMaquina.contains(idMaquina)){

                System.out.println("\nDigite a quantidade a produzir: ");
                double quantidade = SC.nextDouble();

                if (quantidade >= 0){
                    OrdemProducaoDAO ordemProducaoDao = new OrdemProducaoDAO();
                    OrdemProducao ordemProducao = new OrdemProducao(idProduto, idMaquina, quantidade, LocalDate.now(), "PENDENTE");
                    boolean existeOrdemProducao = ordemProducaoDao.buscarExistenciaProducao(ordemProducao);

                    if (!existeOrdemProducao){
                        ordemProducaoDao.inserirOrdemProducao(ordemProducao);
                        maquinaDao.atualizarStatusEmProducao(idMaquina, "EM_PRODUÇÃO");
                    } else {
                        System.out.println("Ordem inválido! Já existe essa ordem cadastrada.");
                    }
                } else {
                    System.out.println("Quantidade inválida! Digite novamente corretamente.");
                }
            } else {
                System.out.println("Não existe ID Digitado! Digite outro ID.");
            }
        } else {
            System.out.println("Não existe ID Digitado! Digite outro ID.");
        }
    }

    private static void cadastrarMateriaPrima() {
        System.out.println("Digite nome da matéria-prima: ");
        String nome = SC.nextLine();

        System.out.println("Digite a quantidade em estoque: ");
        double quantidade = SC.nextDouble();

        if (!nome.isBlank() && quantidade >= 0){
            MateriaPrimaDAO materiaPrimaDao = new MateriaPrimaDAO();
            MateriaPrima materiaPrima = new MateriaPrima(nome, quantidade);
            boolean existeMateriaPrima = materiaPrimaDao.buscarExistenciaMateriaPrima(materiaPrima);
            if (!existeMateriaPrima){
                materiaPrimaDao.inserirMateriaPrima(materiaPrima);
            } else {
                System.out.println("\nCadastro inválido! Já existe esse cadastro.");
            }
        } else {
            System.out.println("Dados inválidos! Digite novamente corretamente.");
        }
    }

    private static void cadastrarProduto() {
        System.out.println("Digite nome do produto: ");
        String nome = SC.nextLine();

        System.out.println("Digite categoria do produto: ");
        String categoria = SC.nextLine();

        if (!nome.isBlank() && !categoria.isBlank()){
            ProdutoDAO produtoDao = new ProdutoDAO();
            Produto produto = new Produto(nome, categoria);
            boolean existeProduto = produtoDao.buscarExistenciaProduto(produto);
            if (!existeProduto){
                produtoDao.inserirProduto(produto);
            } else {
                System.out.println("\nCadastro inválido! Já existe esse cadastro.");
            }
        } else {
            System.out.println("\nDados inválidos! Digite novamente corretamente.");
        }
    }

    private static void cadastrarMaquina() {
        System.out.println("\nDigite nome da máquina: ");
        String nome = SC.nextLine();

        SetorDAO setorDAO = new SetorDAO();
        List<Setor> setorList = setorDAO.listarSetores();
        List<Integer> opcoesSetor = new ArrayList<>();
        for (Setor setor : setorList){
            System.out.println("------ SETOR ------\n" +
                    "ID: " + setor.getId() +
                    "\nNOME: " + setor.getNome() +
                    "\n--------------------");
            opcoesSetor.add(setor.getId());
        }
        System.out.println("Digite ID do setor que deseja: ");
        int idsetor = SC.nextInt();

        SC.nextLine();
        if (!nome.isBlank()) {
            if (opcoesSetor.contains(idsetor)) {
                Maquina maquina = new Maquina(idsetor, nome, "OPERACIONAL");
                MaquinaDAO maquinaDAO = new MaquinaDAO();
                boolean existeSetorMaquina = maquinaDAO.buscarExistencia(maquina);
                if (!existeSetorMaquina) {
                    maquinaDAO.inserirMaquina(maquina);
                } else {
                    System.out.println("\nCadastro inválido! Já existe esse cadastro.");
                    cadastrarMaquina();
                }
            } else {
                System.out.println("\nNão existe ID do setor Digitado! Digite outro ID.");
                cadastrarMaquina();
            }
        } else {
            System.out.println("\nDados inválidos! Digite novamente corretamente.");
            cadastrarMaquina();
        }
    }

    private static void cadastrarSetor() {
        System.out.println("Digite nome do setor: ");
        String nome = SC.nextLine();

        // evitar que receba dados em branco ou espaço em branco
        if (!nome.isBlank()){
            SetorDAO setorDAO = new SetorDAO();
            Setor setor = new Setor(nome);
            // conferir se ja existe mesmo nome
            boolean setorExiste = setorDAO.buscarExistencia(setor);
            if (!setorExiste){
                setorDAO.inserirSetor(setor);
            } else {
                System.out.println("\nCadastro inválido! Já existe esse cadastro.");
            }
        } else {
            System.out.println("\nDados Inválidos! Digite novamente corretamente.");
            cadastrarSetor();
        }
    }
}