(0, '1') -> (1, '1', L)

(1, '^') -> (2, '=', R)

(2, '1') -> (2, '1', R)
(2, 'x') -> (2, 'x', R)
(2, '=') -> (2, '=', R)
(2, '^') -> (3, '^', L)

(3, '1') -> (4, '^', L)
(3, 'x') -> (5, '^', L)
(3, '=') -> (6, '=', L)

(4, '=') -> (4, '=', L)
(4, 'x') -> (4, 'x', L)
(4, '1') -> (4, '1', L)
(4, '^') -> (2, '1', R)

(5, '=') -> (5, '=', L)
(5, 'x') -> (5, 'x', L)
(5, '1') -> (5, '1', L)
(5, '^') -> (2, 'x', R)

(6, 'x') -> (99, 'x', 0)
(6, '^') -> (6, '^', L)
(6, '1') -> (7, '^', L)

(7, '1') -> (7, '1', L)
(7, 'x') -> (8, 'x', L)

(8, '0') -> (8, '0', L)
(8, '1') -> (9, '0', R)
(8, '^') -> (12, '^', R)

(9, '0') -> (9, '0', R)
(9, '1') -> (9, '1', R)
(9, '^') -> (9, '^', R)
(9, 'x') -> (9, 'x', R)
(9, '=') -> (10, '=', R)

(10, '1') -> (10, '1', R)
(10, '^') -> (11, '1', L)

(11, '1') -> (11, '1', L)
(11, '=') -> (11, '=', L)
(11, '^') -> (11, '^', L)
(11, 'x') -> (8, 'x', L)

(12, '0') -> (12, '1', R)
(12, '1') -> (12, '1', R)
(12, 'x') -> (12, 'x', R)
(12, '^') -> (6, '^', L)