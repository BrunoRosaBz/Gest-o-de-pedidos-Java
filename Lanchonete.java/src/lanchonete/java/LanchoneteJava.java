package lanchonete.java;

import java.util.LinkedList;

class Pedido {
    int id;
    String nome;
    String item;
    int quantidade;
    double valor;
    String tipo;
    Pedido proximo;

    Pedido(int id, String nome, String item, int quantidade, double valorBase, String tipo) {
        this.id = id;
        this.nome = nome;
        this.item = item;
        this.quantidade = quantidade;
        this.tipo = tipo;
        if (tipo.equals("Urgente")) {
            this.valor = valorBase * 1.20;
        } else if (tipo.equals("Prioritario")) {
            this.valor = valorBase * 1.10;
        } else {
            this.valor = valorBase;
        }
        this.proximo = null;
    }

    public String toString() {
        return "#" + id + " | " + nome + " | " + quantidade + "x " + item
                + " | R$ " + String.format("%.2f", valor) + " | " + tipo;
    }
}

class ListaManual {
    Pedido inicio;
    int tamanho;

    ListaManual() {
        inicio = null;
        tamanho = 0;
    }

    int tamanho() {
        return tamanho;
    }

    boolean estaVazia() {
        return inicio == null;
    }

    Pedido obterCabeca() {
        return inicio;
    }

    Pedido obterCauda() {
        if (inicio == null) return null;
        Pedido atual = inicio;
        while (atual.proximo != null) {
            atual = atual.proximo;
        }
        return atual;
    }

    void inserirInicio(Pedido p) {
        p.proximo = inicio;
        inicio = p;
        tamanho++;
    }

    void inserirFim(Pedido p) {
        if (inicio == null) {
            inicio = p;
        } else {
            obterCauda().proximo = p;
        }
        tamanho++;
    }

    void inserirMeio(Pedido p) {
        int pos = tamanho / 2;
        if (pos == 0) {
            inserirInicio(p);
            return;
        }
        Pedido anterior = inicio;
        for (int i = 1; i < pos; i++) {
            anterior = anterior.proximo;
        }
        p.proximo = anterior.proximo;
        anterior.proximo = p;
        tamanho++;
    }

    void adicionarPedido(Pedido p) {
        if (p.tipo.equals("Urgente")) {
            inserirInicio(p);
        } else if (p.tipo.equals("Prioritario")) {
            inserirMeio(p);
        } else {
            inserirFim(p);
        }
    }

    Pedido removerInicio() {
        if (inicio == null) return null;
        Pedido removido = inicio;
        inicio = inicio.proximo;
        removido.proximo = null;
        tamanho--;
        return removido;
    }

    Pedido removerFim() {
        if (inicio == null) return null;
        if (inicio.proximo == null) {
            return removerInicio();
        }
        Pedido atual = inicio;
        while (atual.proximo.proximo != null) {
            atual = atual.proximo;
        }
        Pedido removido = atual.proximo;
        atual.proximo = null;
        tamanho--;
        return removido;
    }

    Pedido removerPosicao(int k) {
        if (k < 0 || k >= tamanho) return null;
        if (k == 0) return removerInicio();
        Pedido anterior = inicio;
        for (int i = 1; i < k; i++) {
            anterior = anterior.proximo;
        }
        Pedido removido = anterior.proximo;
        anterior.proximo = removido.proximo;
        removido.proximo = null;
        tamanho--;
        return removido;
    }

    Pedido buscarPorId(int id) {
        Pedido atual = inicio;
        while (atual != null) {
            if (atual.id == id) return atual;
            atual = atual.proximo;
        }
        return null;
    }

    Pedido buscarPorPosicao(int pos) {
        if (pos < 0 || pos >= tamanho) return null;
        Pedido atual = inicio;
        for (int i = 0; i < pos; i++) {
            atual = atual.proximo;
        }
        return atual;
    }

    void imprimirProximos(int n) {
        Pedido atual = inicio;
        int i = 1;
        while (atual != null && i <= n) {
            System.out.println(i + " -> " + atual);
            atual = atual.proximo;
            i++;
        }
    }

    void imprimir() {
        if (inicio == null) {
            System.out.println("(lista vazia)");
            return;
        }
        Pedido atual = inicio;
        int i = 1;
        while (atual != null) {
            System.out.println(i + " -> " + atual);
            atual = atual.proximo;
            i++;
        }
    }
}

public class LanchoneteJava {

    static Pedido[] criarPedidos() {
        return new Pedido[] {
            new Pedido(1,  "Ana",     "X-Burger",         2, 18.00, "Normal"),
            new Pedido(2,  "Bruno",   "Pizza Calabresa",  1, 40.00, "Normal"),
            new Pedido(3,  "Carla",   "Suco de Laranja",  3,  8.00, "Urgente"),
            new Pedido(4,  "Diego",   "X-Salada",         1, 16.00, "Normal"),
            new Pedido(5,  "Elisa",   "Pastel de Queijo", 4,  7.50, "Prioritario"),
            new Pedido(6,  "Fabio",   "Hot Dog",          2, 12.00, "Normal"),
            new Pedido(7,  "Gabi",    "Batata Frita",     1, 15.00, "Normal"),
            new Pedido(8,  "Heitor",  "X-Bacon",          1, 22.00, "Prioritario"),
            new Pedido(9,  "Isabela", "Milkshake",        2, 14.00, "Normal"),
            new Pedido(10, "Joao",    "Coxinha",          6,  6.00, "Urgente"),
            new Pedido(11, "Karen",   "Acai 500ml",       1, 20.00, "Normal"),
            new Pedido(12, "Lucas",   "Refrigerante",     2,  7.00, "Prioritario"),
            new Pedido(13, "Marina",  "Sanduiche Natural",1, 13.00, "Normal"),
            new Pedido(14, "Nando",   "Pizza Frango",     1, 42.00, "Urgente"),
            new Pedido(15, "Olivia",  "Esfiha",           5,  5.50, "Prioritario")
        };
    }

    public static void main(String[] args) {

        System.out.println(" VERSAO 1: LISTA MANUAL ");
        ListaManual lista = new ListaManual();

        for (Pedido p : criarPedidos()) {
            lista.adicionarPedido(p);
        }

        System.out.println("\n Lista em ordem de atendimento ");
        lista.imprimir();
        System.out.println("Tamanho: " + lista.tamanho());
        System.out.println("Esta vazia? " + lista.estaVazia());
        System.out.println("Cabeca: " + lista.obterCabeca());
        System.out.println("Cauda: " + lista.obterCauda());

        System.out.println("\n Buscas ");
        System.out.println("Pedido de id 8: " + lista.buscarPorId(8));
        System.out.println("Pedido na posicao 5: " + lista.buscarPorPosicao(5));
        System.out.println("Proximos 3 pedidos:");
        lista.imprimirProximos(3);

        System.out.println("\n Remocoes ");
        System.out.println("Atendido (inicio): " + lista.removerInicio());
        System.out.println("Cancelado (fim): " + lista.removerFim());
        System.out.println("Cancelado (posicao 4): " + lista.removerPosicao(4));

        System.out.println("\n  Lista depois das remocoes ");
        lista.imprimir();
        System.out.println("Tamanho: " + lista.tamanho());

        System.out.println("\n VERSAO 2: LinkedList DO JAVA ");
        LinkedList<Pedido> fila = new LinkedList<>();

        for (Pedido p : criarPedidos()) {
            if (p.tipo.equals("Urgente")) {
                fila.addFirst(p);
            } else if (p.tipo.equals("Prioritario")) {
                fila.add(fila.size() / 2, p);
            } else {
                fila.addLast(p);
            }
        }

        System.out.println("\n Lista em ordem de atendimento ");
        int posicao = 1;
        for (Pedido p : fila) {
            System.out.println(posicao + " -> " + p);
            posicao++;
        }
        System.out.println("Tamanho: " + fila.size());
        System.out.println("Esta vazia? " + fila.isEmpty());
        System.out.println("Cabeca: " + fila.getFirst());
        System.out.println("Cauda: " + fila.getLast());

        System.out.println("\n Buscas");
        Pedido achado = null;
        for (Pedido p : fila) {
            if (p.id == 8) {
                achado = p;
            }
        }
        System.out.println("Pedido de id 8: " + achado);
        System.out.println("Pedido na posicao 5: " + fila.get(5));
        System.out.println("Proximos 3 pedidos:");
        for (int i = 0; i < 3 && i < fila.size(); i++) {
            System.out.println((i + 1) + " -> " + fila.get(i));
        }

        System.out.println("\n Remocoes ");
        System.out.println("Atendido (inicio): " + fila.removeFirst());
        System.out.println("Cancelado (fim): " + fila.removeLast());
        System.out.println("Cancelado (posicao 4): " + fila.remove(4));

        System.out.println("\n Lista depois das remocoes ");
        posicao = 1;
        for (Pedido p : fila) {
            System.out.println(posicao + " -> " + p);
            posicao++;
        }
        System.out.println("Tamanho: " + fila.size());
    }
}