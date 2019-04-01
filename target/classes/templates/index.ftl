<#import "layout/layout.ftl" as Layout/>
<@Layout.mainLayout>
<context:property-placeholder location="classpath:/application.properties" />

<script src="http://code.jquery.com/jquery-1.7.1.min.js"></script>

<div class="block">
    <div class="block-title">
        <div id="headerText"><h2>Google Plus Image Ripper</h2></div>
    </div>
    <div id="urlForm">

        <form class="form-horizontal" id="search-form">

          <label for="bth-search">Album Grab</label>
          <button type="submit" id="bth-search"
                    class="btn btn-primary btn-sm btn-space">Start Album Grab
          </button>
          <button type="submit" id="bth-cancel"
                    class="btn btn-danger btn-sm btn-space">Cancel Album Grab
          </button>
          <br/>
          <br/>
          <label for="bth-parse">Photo Grab</label>
          <button type="submit" id="bth-parse"
                  class="btn btn-primary btn-sm btn-space">Start Photo Grab
          </button>
          <button type="submit" id="bth-cancelparse"
                  class="btn btn-danger btn-sm btn-space">Cancel Photo Grab
          </button>
       </form>
    </div>
</div>

<div id="progressBarContainer"></div>

<div id="feedback"></div>

</@Layout.mainLayout>

