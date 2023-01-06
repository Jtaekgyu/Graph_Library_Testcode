package com.example.com.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jgrapht.graph.DefaultWeightedEdge;

@Getter
@AllArgsConstructor
public class EdgeLine extends DefaultWeightedEdge { 
    // EdgeLine이 DefaultWeightedEdge를 상속받아야지만 setEdgeWeight(E e, double weight) 코드 사용가능

    private Long lineId;
    private City startV;// 시작점
    private City endV;  // 도착점
    private Integer distance;   // 가중치
}
