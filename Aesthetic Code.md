### Set

通过add()结果来判断是否存在元素

```java
// Complicated
List<Person> source = new ArrayList<>();
Set<String> pSet = new HashSet<>();
List<Person> target = new ArrayList<>();
for (Person p : source) {
    if (!pSet.contains(p.getName())) {
        pSet.add(p.getName());
        target.add(p);
    }
}

// Aesthetic
List<Person> source = new ArrayList<>();
Set<String> pSet = new HashSet<>();
List<Person> target = new ArrayList<>();
for (Person p : source) {
    if (pSet.add(p.getName())) {
        target.add(p);
    }
}
```

### Map.computeIfAbsent

```java
// Complicated
Map<Long, List<UserDO>> roleUserMap = new HashMap<>();
for (UserDO userDO : userDOList) {
    Long roleId = userDO.getRoleId();
    List<UserDO> userList = roleUserMap.get(roleId);
    if (Objects.isNull(userList)) {
        userList = new ArrayList<>();
        roleUserMap.put(roleId, userList);
    }
    userList.add(userDO);
}

// Aesthetic
Map<Long, List<Person>> roleUserMap = new HashMap<>();
for (UserDO userDO : userDOList) {
    roleUserMap.computeIfAbsent(userDO.getRoleId(), key -> new ArrayList<>())
        .add(userDO);
}
```

### 判断集合空值

```java
if (userList != null && !userList.isEmpty()) {
    // TODO: 处理代码
}


if (CollectionUtils.isNotEmpty(userList)) {
    // TODO: 处理代码
}
```



### 值过滤

```java
Integer thisValue;
if (Objects.nonNull(value) && value.compareTo(MAX_VALUE) <= 0) {
    thisValue = value;
} else {
    thisValue = MAX_VALUE;
}

Integer thisValue = Optional.ofNullable(value)
    .filter(tempValue -> tempValue.compareTo(MAX_VALUE) <= 0).orElse(MAX_VALUE);
```



### 多层次对象判空

使用Optional

```java

String zipcode = null;
if (Objects.nonNull(user)) {
    Address address = user.getAddress();
    if (Objects.nonNull(address)) {
        Country country = address.getCountry();
        if (Objects.nonNull(country)) {
            zipcode = country.getZipcode();
        }
    }
}

tring zipcode = Optional.ofNullable(user).map(User::getAddress)
    .map(Address::getCountry).map(Country::getZipcode).orElse(null);
```



### 分组List为Map

```java
Map<Long, List<UserDO>> roleUserMap = new HashMap<>();
for (UserDO userDO : userDOList) {
    roleUserMap.computeIfAbsent(userDO.getRoleId(), key -> new ArrayList<>())
        .add(userDO);
}

Map<Long, List<UserDO>> roleUserMap = userDOList.stream()
    .collect(Collectors.groupingBy(UserDO::getRoleId));
```

### 分组汇总集合

```java
Map<Long, Double> roleTotalMap = new HashMap<>();
for (Account account : accountList) {
    Long roleId = account.getRoleId();
    Double total = Optional.ofNullable(roleTotalMap.get(roleId)).orElse(0.0D);
    roleTotalMap.put(roleId, total + account.getBalance());
}

roleTotalMap = accountList.stream().collect(Collectors.groupingBy(Account::getRoleId, Collectors.summingDouble(Account::getBalance)));
```

### 生成序列

```java
int[] array1 = IntStream.rangeClosed(1, N).toArray();
int[] array2 = IntStream.iterate(1, n -> n * 2).limit(N).toArray();
```

