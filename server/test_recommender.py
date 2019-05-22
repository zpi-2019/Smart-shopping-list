from recommendation import Recommender
from scipy.spatial.distance import pdist
import numpy as np
from unittest import TestCase
from unittest.mock import Mock


class TestRecommender(TestCase):
    def setUp(self):
        self.product_num = 4
        self.rec = Recommender(self.product_num)

        self.rec._glove = Mock()
        self.rec._glove.id2word = {0: 'ciastka', 1: 'mleko', 2: 'kawa', 3: 'woda'}

    @staticmethod
    def _dist(p1, p2):
        return ((p1[0] - p2[0]) ** 2 + (p1[0] - p2[0]) ** 2) ** .5

    def test__reformat_vector(self):
        id2word = self.rec._glove.id2word
        points = np.array([
            [1, 1],
            [5, 5],
            [3, 3],
            [12, 12],
        ])
        vec = pdist(points)
        result = self.rec._reformat_vector(vec)

        self.assertIsInstance(result, dict)

        self.assertIn('ciastka', result.keys())
        self.assertIn('mleko', result.keys())
        self.assertIn('kawa', result.keys())
        self.assertIn('woda', result.keys())

        dist = result[id2word[0]]
        self.assertEqual(len(dist), self.product_num - 1)
        self.assertEqual(dist[0], self._dist(points[0], points[1]))
        self.assertEqual(dist[1], self._dist(points[0], points[2]))
        self.assertEqual(dist[2], self._dist(points[0], points[3]))

        dist = result[id2word[1]]
        self.assertEqual(len(dist), self.product_num - 2)
        self.assertEqual(dist[0], self._dist(points[1], points[2]))
        self.assertEqual(dist[1], self._dist(points[1], points[3]))

        dist = result[id2word[2]]
        self.assertEqual(len(dist), self.product_num - 3)
        self.assertEqual(dist[0], self._dist(points[2], points[3]))
