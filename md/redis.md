## Redis

### cli 접속
```text
docker exec -it [container-id] redis-cli --raw
```
> raw 는 한글이 깨지는 경우

[redis 자세히 보기](https://redis.io/docs/data-types/tutorial/)

### String 자료 구조 ( key - value )
```text
$ set id 10 // key(id) 의 value를 10으로 저장
$getid //key조회
$delid //key삭제
$scan0 //key들을일정단위개수만큼씩조회
```

### Hash 자료 구조 (key - subKey - value)
```text
$ hgetall USER // Key(USER)의 매핑되는 모든 필드과 값들을 조회
$ hset USER subkey value // Key(USER)의 subKey 의 값을 지정
$ hget USER subkey // Key(USER)의 subKey 의 값을 조회
```

### geopoints1 라는 자료구조에 restaurant1, 2 각각 경도, 위도를 추가
```text
$ geoadd geopoints1 127.0817 37.5505 restaurant1
$ geoadd geopoints1 127.0766 37.541 restaurant2
// 두 지역의 거리를 리턴한다. 단위는 km
$ geodist geopoints1 restaurant1 restaurant2 km
```

### Test Code 2
```text
$ geoadd geopoints2 127.0569046 37.61040424 restaurant1
$ geoadd geopoints2 127.029052 37.60894036 restaurant2
$ geoadd geopoints2 127.236707811313 37.3825107393401 restaurant3
// geopoints2 이름의 자료구조에서 주어진 위도, 경도 기준으로 반경 10km 이내에 가까운 약국 찾기
•
$ georadius geopoints2 127.037033003036 37.596065045809 10 km withdist withcoord asc count 3
```