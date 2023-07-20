(ns mini_game.jokenpo)

(defn venceu-jogo?
  [vitorias]
  (>= vitorias 5))

(defn jokenpo-extensão-the-big-bang-theory
  []
  (let [movimentos-e-vantagens     {:papel   #{:pedra :spock}
                                    :pedra   #{:tesoura :lagarto}
                                    :tesoura #{:papel :lagarto}
                                    :spock   #{:tesoura :pedra}
                                    :lagarto #{:spock :papel}}
        jogadas-vencidas-jogador-1 (atom 0)
        jogadas-vencidas-jogador-2 (atom 0)
        venceu-partida  (fn [escolha-jogador-1 escolha-jogador-2]
                                     (let [vantagens-jogador-1 (get movimentos-e-vantagens escolha-jogador-1)
                                           vantagens-jogador-2 (get movimentos-e-vantagens escolha-jogador-2)]
                                       (cond
                                         (contains? vantagens-jogador-1 escolha-jogador-2) 1
                                         (contains? vantagens-jogador-2 escolha-jogador-1) 2
                                         :else nil)))]
    (while (and (not (venceu-jogo? @jogadas-vencidas-jogador-1))
                (not (venceu-jogo? @jogadas-vencidas-jogador-2)))
      (let [escolha-jogador-1 (rand-nth (keys movimentos-e-vantagens))
            escolha-jogador-2 (rand-nth (keys movimentos-e-vantagens))
            jogador-vitorioso (venceu-partida escolha-jogador-1 escolha-jogador-2)]
        (println (format "Jogador 1 escolhe %s" escolha-jogador-1))
        (println (format "Jogador 2 escolhe %s" escolha-jogador-2))

        (if-not (nil? jogador-vitorioso)
          (do
            (println (format "Jogador %s venceu" jogador-vitorioso))
            (if (= jogador-vitorioso 1)
              (do
                (swap! jogadas-vencidas-jogador-1 inc)
                (println (format "Jogador 1 venceu %s vezes" @jogadas-vencidas-jogador-1)))
              (do
                (swap! jogadas-vencidas-jogador-2 inc)
                (println (format "Jogador 2 venceu %s vezes" @jogadas-vencidas-jogador-2)))))
          (println "Deu empate garaio"))))

    (println (format "Jogo finalizado com o jogador %s sendo vitorioso"
                     (if (venceu-jogo? @jogadas-vencidas-jogador-1)
                       1
                       2)))))
(jokenpo-extensão-the-big-bang-theory)


(defn pedra-papel-tesoura-tradicional
  []
  (let [movimentos-e-vantagens     {:papel   #{:pedra }
                                    :pedra   #{:tesoura}
                                    :tesoura #{:papel}}
        jogadas-vencidas-jogador-1 (atom 0)
        jogadas-vencidas-jogador-2 (atom 0)
        escolha-aleatoria          (fn [] (rand-nth (keys movimentos-e-vantagens)))
        venceu-partida        (fn [escolha-jogador-x escolha-jogador-y]
                                     (contains?
                                       (get movimentos-e-vantagens escolha-jogador-x)
                                       escolha-jogador-y))
        acabou                    (fn [jogadas-vencidas-jogador-1 jogadas-vencidas-jogador-2]
                                     (or (venceu-jogo? jogadas-vencidas-jogador-1)
                                         (venceu-jogo? jogadas-vencidas-jogador-2)))]
    (while (not (acabou @jogadas-vencidas-jogador-1 @jogadas-vencidas-jogador-2))
      (let [escolha-jogador-1 (escolha-aleatoria)
            escolha-jogador-2 (escolha-aleatoria)]
        (println (format "Jogador 1 escolhe %s" escolha-jogador-1))
        (println (format "Jogador 2 escolhe %s" escolha-jogador-2))

        (cond
          (venceu-partida escolha-jogador-1 escolha-jogador-2)
          (do
            (swap! jogadas-vencidas-jogador-1 inc)
            (println (format "Jogador 1 venceu %s vezes" @jogadas-vencidas-jogador-1)))

          (venceu-partida escolha-jogador-2 escolha-jogador-1)
          (do
            (swap! jogadas-vencidas-jogador-2 inc)
            (println (format "Jogador 2 venceu %s vezes" @jogadas-vencidas-jogador-2)))

          :else
          (println "Deu empate garaio"))))

    (println (format "Jogo finalizado com o jogador %s sendo vitorioso"
                     (if (venceu-jogo? @jogadas-vencidas-jogador-1) 1 2)))))

(pedra-papel-tesoura-tradicional)