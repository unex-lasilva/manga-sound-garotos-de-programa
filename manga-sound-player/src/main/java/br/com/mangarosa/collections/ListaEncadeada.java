package br.com.mangarosa.collections;

public class ListaEncadeada {
    private No cabeca;
    private int tamanho;

    public ListaEncadeada() {
        cabeca = null;
        tamanho = 0;
    }

    public void append(Object value) {
        No novo = new No(value);
        if (cabeca == null) {
            cabeca = novo;
        } else {
            No atual = cabeca;
            while (atual.getProx() != null) {
                atual = atual.getProx();
            }
            atual.setProx(novo);
        }
        tamanho++;
    }

    public void insertAt(int position, Object value) {
        if (position < 0 || position > tamanho) {
            throw new IndexOutOfBoundsException("Posição inválida");
        }

        No novo = new No(value);
        if (position == 0) {
            novo.setProx(cabeca);
            cabeca = novo;
        } else {
            No atual = cabeca;
            for (int i = 0; i < position - 1; i++) {
                atual = atual.getProx();
            }
            novo.setProx(atual.getProx());
            atual.setProx(novo);
        }
        tamanho++;
    }

    public void addAll(ListaEncadeada list) {
        for (int i = 0; i < list.size(); i++) {
            this.append(list.get(i));
        }
    }

    public boolean remove(int position) {
        if (position < 0 || position >= tamanho) return false;
        if (position == 0) {
            cabeca = cabeca.getProx();
        } else {
            No atual = cabeca;
            for (int i = 0; i < position - 1; i++) {
                atual = atual.getProx();
            }
            atual.setProx(atual.getProx().getProx());
        }
        tamanho--;
        return true;
    }

    public boolean clear() {
        cabeca = null;
        tamanho = 0;
        return true;
    }

    public Object get(int position) {
        if (position < 0 || position >= tamanho) {
            throw new IndexOutOfBoundsException("Posição inválida");
        }
        No atual = cabeca;
        for (int i = 0; i < position; i++) {
            atual = atual.getProx();
        }
        return atual.getValor();
    }

    public boolean isEmpty() {
        return tamanho == 0;
    }

    public int size() {
        return tamanho;
    }

    public int indexOf(Object value) {
        No atual = cabeca;
        for (int i = 0; i < tamanho; i++) {
            if ((atual.getValor() == null && value == null) ||
                    (atual.getValor() != null && atual.getValor().equals(value))) {
                return i;
            }
            atual = atual.getProx();
        }
        return -1;
    }

    public boolean contains(Object value) {
        return indexOf(value) != -1;
    }
}
