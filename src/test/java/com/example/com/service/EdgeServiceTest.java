package com.example.com.service;

import com.example.com.domain.City;
import com.example.com.domain.EdgeLine;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.WeightedMultigraph;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest
public class EdgeServiceTest {

    @Test
    public void edgeTest(){
        City city1 = new City(1L, "서울");
        City city2 = new City(2L, "알래스카");
        City city3 = new City(3L, "LA");
        WeightedMultigraph<City, EdgeLine> graph = new WeightedMultigraph<City, EdgeLine>(EdgeLine.class);

        graph.addVertex(city1);
        graph.addVertex(city2);
        graph.addVertex(city3);

        EdgeLine edge1 = new EdgeLine(1L, city1, city2, 6000); // 서울-알래스카
        EdgeLine edge2 = new EdgeLine(2L, city2, city3, 3700); // 알래스카-LA
        EdgeLine edge3 = new EdgeLine(3L, city1, city3, 12000); // 서울-LA
        graph.addEdge(edge1.getStartV(), edge1.getEndV(), edge1);
        graph.addEdge(edge2.getStartV(), edge2.getEndV(), edge2);
        graph.addEdge(edge3.getStartV(), edge3.getEndV(), edge3);

        // Edge 가중치 등록
        graph.setEdgeWeight(edge1, edge1.getDistance());
        graph.setEdgeWeight(edge2, edge2.getDistance());
        graph.setEdgeWeight(edge3, edge3.getDistance());

        // Dijkstra Algorithm은 Shortest Path Alogrithm 중에서 Single source path를 찾는 알고리즘
        // (단일 정점 V에서 출발하여 모든 다른 정점으로 도착하는 최단 경로를 찾는 방법)
        DijkstraShortestPath<City, EdgeLine> shortestPath = new DijkstraShortestPath<>(graph);
        GraphPath<City, EdgeLine> path = shortestPath.getPath(city1, city3); // 서을-LA의 최단 경로
        List<EdgeLine> edgesInOrder = path.getEdgeList(); // 최단 경로를 가지는 간선들의 정보가 순서데로 List에 들어감
        List<City> vertexInOrder = path.getVertexList(); // 최단 경로를 거치는 Vertex들의 순서가 List로 들어감
        double distance = path.getWeight();

        // containsExactly - 순서를 포함해서 정확히 일치하는지 체크함
        assertThat(edgesInOrder).containsExactly(edge1, edge2);
        assertThat(vertexInOrder).containsExactly(city1, city2, city3);
        assertThat(distance).isEqualTo(9700);
    }
}
