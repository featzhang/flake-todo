# datepicker 默认展开

```java
DatePicker datePicker=new DatePicker(LocalDate.now());
DatePickerSkin datePickerSkin=new DatePickerSkin(datePicker);
Node popupContent=datePickerSkin.getPopupContent();
datePickerPane.setCenter(popupContent);
```