# README.md

```bash
    [종속성]
    org.geotools
    
    [디렉토리 구조]
    - parking
    - shp
        - busstop.shp
        - crossedge.shp
        - crosswalk.shp
        - fireplug.shp
        - RDL_SCHO_AS.shp
```

## 테스트 코드

```java
Parking parking = new Parking();
parking.parkingReadout(x, y, time);
```

- x: 위도, y: 경도, time: 시간(시간 단위로 입력)

## 출력 설명

```text
0 : 어린이보호구역 위반
1 : 버스정류장 주정차 금지구역 위반
2 : 교차로 주정차 금지구역 위반
3 : 횡단보도 주정차 금지구역 위반
4 : 소화전 주정차 금지구역 위반
-1 : 위반 사항 없음
```
