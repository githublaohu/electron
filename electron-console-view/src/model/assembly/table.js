function TableView() {

    this.__isPagination = true;

    this.__pagination = {
        currentPage: 1,
        pageSize: this.__pageSize,
        total: 0
    };

    this.__columns = [];

    this.__columnsData = [];

    this.__userTables = true;

    this.__delete = true;

    this.__deleteKey = null;

    this.__deleteValue = null

    this.__activation= true;

    this.__update = true;

    this.__query = true;

    this.__key = null;

    this.__pageSize = 10;

    this.clone = function(){
        var tableView = new TableView();
        tableView.__isPagination = this.__isPagination;
        tableView.__pagination = JSON.parse( JSON.stringify( this.__pagination));
        tableView.__columns = this.__columns;
        tableView.__userTables = this.__userTables;
        tableView.__delete = this.__delete;
        tableView.__deleteKey = this.__deleteKey;
        tableView.__deleteValue = this.__deleteValue;
        tableView.__activation = this.__activation;
        tableView.__update = this.__update;
        tableView.__query = this.__query;
        tableView.__key = this.__key;
        tableView.__pageSize = this.__pageSize;
        return tableView;
    }

    this.deleteView = function(row){
        return this.__delete && this.__deleteValue === row[this.__deleteKey]
    }

    this.activationView = function(row){
        return this.__delete && this.__activation && this.__deleteValue != row[this.__deleteKey]
    }

    this.setPagination = function(pagination){
        this.__pagination = pagination;
    }

    this.resetPagination = function(){
        this.__pagination = {
            currentPage: 1,
            pageSize: this.__pageSize,
            total: 0
        };
    }
}

export{TableView}