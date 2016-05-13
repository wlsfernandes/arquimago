<div class="style-select">
    <div class="stalabel"> <i class="fa fa-cog fa-spin"></i></div>
    <div class="stylepanel">
        <form method="post" role="form">

            <input type="hidden" name="submitform">

            <label>Select skin</label>
            <select class="form-control syleselecta" name="stile">
            <option value="blank.css">blank</option><option value="cs-black.css">cs-black</option><option value="cs-blue.css">cs-blue</option><option value="cs-brown.css">cs-brown</option><option value="cs-green.css">cs-green</option><option value="cs-navy.css">cs-navy</option><option value="cs-purple.css">cs-purple</option><option value="cs-red.css">cs-red</option><option value="cs-sand.css">cs-sand</option><option value="cs-white.css">cs-white</option><option value="cs-yellow.css">cs-yellow</option><option value="vfm-2016.css"selected >vfm-2016</option>                    </select>

            <div class="checkbox">
                <label>
                    <input type="checkbox" name="showhead" class="checkhead"
                    checked                            > Header
                </label>
            </div>

            <div class="checkbox">
                <label>
                    <input type="checkbox" name="showdesc" class="checkhead"
                    checked                            > Description
                </label>
            </div>
            <div class="checkbox">
                <label>
                    <input type="checkbox" name="inlinethumbs" class="checkhead"
                    checked                            > Inline thumbnails
                </label>
            </div>
            <button type="submit" class="btn btn-info btn-block"><i class="fa fa-refresh"></i> update</button>
        </form>
    </div>
</div>