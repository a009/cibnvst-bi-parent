<%@ page language="java" pageEncoding="UTF-8"%>
<!--<div style="text-align:center; margin-top:20px;">-->
<!--<div>-->
<!--	<input name="button" type="submit" class="submit02" id="button" value="提交" />&nbsp;&nbsp;-->
<!-- 	<input name="reset" type="reset" class="submit03" id="reset" value="重置" />&nbsp;&nbsp;-->
<!-- 	<input type="button" onclick="javascript:window.history.go(-1)" class="submit03" value="返回" />-->
<!--</div>-->

<!-- 不使用name属性的原因，新增修改后name值会传到查询页面，影响查询 -->
<div class="btnfooter">
    <input type="submit" class="btnSubmit" id="btnSubmit" value="提交" />
    <input type="reset" class="btnReset" value="重置" />
    <input type="button" class="btnReset" onclick="javascript:window.history.go(-1)" value="返回" />
</div>

