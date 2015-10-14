<div class="col-sm-6 col-sm-offset-3"><div class="panel panel-default">
  <div class="panel-heading">Input Password</div>
    <div class="panel-body">
      <form class="form-horizontal" action="../../Contest.jsp" method="get">
        <div class="form-group">
          <label for="Password" class="col-sm-2 control-label">Password</label>
          <div class="col-sm-10">
            <input type="text" class="form-control" id="Password" placeholder="Password" name="password">
          </div>
        </div>
        <div class="form-group hidden">
          <label for="Password" class="col-sm-2 control-label">cid</label>
          <div class="col-sm-10">
            <input type="text" class="form-control" id="cid" placeholder="cid" name="cid" value=<%=cid%> readonly>
          </div>
        </div>
        <div class="form-group">
          <div class="col-sm-offset-2 col-sm-10">
            <button type="submit" class="btn btn-default">Login</button>
          </div>
        </div>
      </form>
    </div>
  </div>
</div>