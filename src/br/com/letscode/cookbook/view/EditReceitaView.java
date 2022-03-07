package br.com.letscode.cookbook.view;

import br.com.letscode.cookbook.domain.Ingrediente;
import br.com.letscode.cookbook.domain.Receita;
import br.com.letscode.cookbook.domain.Rendimento;
import br.com.letscode.cookbook.enums.Categoria;
import br.com.letscode.cookbook.enums.TipoMedida;
import br.com.letscode.cookbook.enums.TipoRendimento;

import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class EditReceitaView {
    private Receita receita;

    public EditReceitaView(Receita receita) {
        this.receita = new Receita(receita);
    }

    public Receita edit() {
        boolean onEdit = true;
        while (onEdit) {
            new ReceitaView(receita).fullView(System.out);
            onEdit = showMenuReceita();
        }

        new ReceitaView(receita).fullView(System.out);
        if (ConsoleUtils.getUserOption("Você deseja salvar a receita?\tS - Sim   N - Não", "S", "N").equalsIgnoreCase("S")) {
            return receita;
        } else {
            return null;
        }
    }

    private boolean showMenuReceita() {
        String[] options = new String[9];
        StringBuilder sb = new StringBuilder("#".repeat(100))
                .append("%n").append("# O que você deseja alterar?");
        options[0] = "N";
        sb.append("%n# ").append("N : Nome da receita");
        options[1] = "C";
        sb.append("%n# ").append("C : Categoria da receita");
        options[2] = "T";
        sb.append("%n# ").append("T : Tempo de preparo da receita");
        options[3] = "R";
        sb.append("%n# ").append("R : Rendimento da receita");
        options[4] = "I";
        sb.append("%n# ").append("I : Ingredientes da receita");
        options[5] = "P";
        sb.append("%n# ").append("P : Modo de preparo da receita");
        options[6] = "X";
        sb.append("%n# ").append("X : Sair da edição da receita");
        sb.append("%n").append("#".repeat(100));

        String opcao = ConsoleUtils.getUserOption(sb.toString(), options).toUpperCase(Locale.getDefault());
        switch (opcao) {
            case "N":
                editNome();
                break;
            case "C":
                editCategoria();
                break;
            case "T":
                editTempoPreparo();
                break;
            case "R":
                editRendimento();
                break;
            case "I":
                showMenuIngredientes();
                break;
            case "P":
                showMenuPreparos();
                break;
            default:
                return false;
        }
        return true;
    }

    private void showMenuIngredientes() {
        do {
            String[] options = new String[9];
            StringBuilder sb = new StringBuilder("#".repeat(100))
                    .append("%n").append("# Selecione uma opção?");
            options[0] = "+";
            sb.append("%n# ").append("+ : Adicionar Ingrediente");
            if (receita.getIngredientes().size() > 0) {
                options[1] = "-";
                sb.append("%n# ").append("- : Remover Ingrediente");
            }
            options[2] = "X";
            sb.append("%n# ").append("X : Voltar para edição da receita");
            sb.append("%n").append("#".repeat(100));

            String opcao = ConsoleUtils.getUserOption(sb.toString(), options).toUpperCase(Locale.getDefault());
            if (opcao.equalsIgnoreCase("+")) {
                addIngrediente();
            } else if (opcao.equalsIgnoreCase("+")) {
                delIngrediente();
            } else {
                return;
            }
            new ReceitaView(receita).fullView(System.out);
        } while (true);
    }

    private void showMenuPreparos() {
        do {
            String[] options = new String[9];
            StringBuilder sb = new StringBuilder("#".repeat(100))
                    .append("%n").append("# Selecione uma opção?");
            options[0] = "+";
            sb.append("%n# ").append("+ : Adicionar Preparo");
            if (receita.getPreparo().size() > 0) {
                options[1] = "-";
                sb.append("%n# ").append("- : Remover Preparo");
            }
            options[2] = "X";
            sb.append("%n# ").append("X : Voltar para edição da receita");
            sb.append("%n").append("#".repeat(100));

            String opcao = ConsoleUtils.getUserOption(sb.toString(), options).toUpperCase(Locale.getDefault());
            if (opcao.equalsIgnoreCase("+")) {
                addPreparo();
            } else if (opcao.equalsIgnoreCase("+")) {
                delPreparo();
            } else {
                return;
            }
            new ReceitaView(receita).fullView(System.out);
        } while (true);
    }

    private void editNome() {
        String name = ConsoleUtils.getUserInput("Qual o novo nome da receita?");
        if ((name != null) && (!name.isBlank())) {
            receita.setNome(name);
        }
    }

    private void editCategoria() {
        StringBuilder sb = new StringBuilder("Qual a nova categoria da receita?\n");
        String[] options = new String[Categoria.values().length];
        for (int i = 0; i < options.length; i++) {
            options[i] = String.valueOf(i);
            sb.append(String.format("%d - %s%n", i, Categoria.values()[i]));
        }
        String opcao = ConsoleUtils.getUserOption(sb.toString(), options);
        Categoria categoria = null;
        for (int i = 0; i < options.length; i++) {
            if (opcao.equalsIgnoreCase(options[i])) {
                categoria = Categoria.values()[i];
                break;
            }
        }
        receita.setCategoria(categoria);
    }

    private void editTempoPreparo() {
        do {
            String time = ConsoleUtils.getUserInput("Qual o novo tempo de preparo da receita?");
            if (!time.isBlank()) {
                try {
                    double value = Double.parseDouble(time);
                    receita.setTempoPreparo(value);
                    return;
                } catch (NullPointerException | NumberFormatException e) {
                    System.out.println("Tempo de preparo invalido.");
                }
            } else {
                break;
            }
        } while (true);
    }

    public void editRendimento() {
        int valueMin;
        int valueMax;
        do {
            String min = ConsoleUtils.getUserInput("Qual o rendimento minimo da receita?");
            try {
                if (min != null) {
                    valueMin = Integer.parseInt(min);
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Rendimento minimo invalido.");
            }
        } while (true);
        do {
            String max = ConsoleUtils.getUserInput("Qual o rendimento máximo da receita?");
            try {
                if (max != null) {
                    valueMax = Integer.parseInt(max);
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Rendimento máximo invalido.");
            }
        } while (true);

        TipoRendimento tipoRendimento = (TipoRendimento) selecionarOpcoes(TipoRendimento.values(), "Qual o tipo de rendimento da receita?");
        if (valueMin == valueMax) {
            receita.setRendimento(new Rendimento(valueMin, tipoRendimento));
        } else {
            receita.setRendimento(new Rendimento(valueMin, valueMax, tipoRendimento));
        }
    }

    private Enum selecionarOpcoes(Enum[] values, String question) {
        int opcao = selecionarOpcoes((String[]) Arrays.stream(values).map(anEnum -> anEnum.toString()).collect(Collectors.toList()).toArray(new String[]{}), question);
        if (opcao < 0) {
            return null;
        } else {
            return values[opcao];
        }
    }

    private int selecionarOpcoes(String[] values, String question) {
        StringBuilder sb = new StringBuilder(question);
        String[] options = new String[values.length];
        for (int i = 0; i < options.length; i++) {
            options[i] = String.valueOf(i);
            sb.append("%n# ").append(String.format("%d : %s", i, values[i]));
        }
        String opcao = ConsoleUtils.getUserOption(sb.toString(), options);
        for (int i = 0; i < options.length; i++) {
            if (opcao.equalsIgnoreCase(options[i])) {
                return i;
            }
        }
        return -1;
    }

    private void addIngrediente() {
        double quantidade;
        String name = ConsoleUtils.getUserInput("Qual o nome do ingrediente?");
        if (name.isBlank()) {
            return;
        }

        do {
            String qtd = ConsoleUtils.getUserInput("Qual a quantidade do ingrediente?");
            try {
                if (qtd != null) {
                    quantidade = Double.parseDouble(qtd);
                    break;
                }
            } catch (NullPointerException | NumberFormatException e) {
                System.out.println("Quantidade do ingrediente invalida.");
            }
        } while (true);

        TipoMedida tipoMedida = (TipoMedida) selecionarOpcoes(TipoMedida.values(), "Qual o tipo de medida do ingrediente?");
        receita.getIngredientes().add(new Ingrediente(name, quantidade, tipoMedida));
    }

    private void delIngrediente() {
        int opcao = selecionarOpcoes((String[]) receita.getIngredientes().stream().map(Object::toString).collect(Collectors.toList()).toArray(new String[]{}), "Qual o ingrediente que você deseja remover?");
        if (opcao < 0) {
            receita.getIngredientes().remove(opcao);
        }
    }

    private void addPreparo() {
        String preparo = ConsoleUtils.getUserInput("Entre com o passo de preparo a incluir");
        if (preparo.isBlank()) {
            return;
        }
        int posicao = 0;
        if (!receita.getPreparo().isEmpty()) {
            posicao = selecionarOpcoes((String[]) IntStream.rangeClosed(0, receita.getPreparo().size()).mapToObj(i -> i == receita.getPreparo().size() ? "No final da lista" : String.format("Passo %d", i)).collect(Collectors.toList()).toArray(new String[]{}), "O novo preparo será adicionado antes de qual passo?");
        }
        receita.getPreparo().add(posicao, preparo);
    }

    private void delPreparo() {
        int opcao = selecionarOpcoes((String[]) receita.getPreparo().toArray(), "Qual o passo do preparo que você deseja remover?");
        if (opcao < 0) {
            receita.getPreparo().remove(opcao);
        }
    }
}
