function RouteInfo(modeManage, mainRoute) {

    this.__path;

    this.__title;

    this.__name;

    this.__modelImport;

    this.__viewImport;

    this.__icon;

    this.__meta = {};

    this.__routeConfig = {
        meta: {
            noCache: true
        }
    }

    this.__mainRoute = mainRoute;

    this.path = function (path) {
        this.path = path
        this.__routeConfig["path"] = path
        return this;
    }

    this.title = function (title) {
        this.__title = title;
        this.__routeConfig.meta["title"] = title;
        return this;
    }

    this.meta = function(key , value){
        this.__routeConfig.meta[key] = value;
        return this;
    }

    this.names = function (name) {
        this.__name = name;
        this.__routeConfig["name"] = name;
        return this;
    }

    this.modelImport = function (modelImport) {
        this.__modelImport = modelImport;
        return this;
    }

    this.viewImport = function (viewImport) {
        this.__viewImport = viewImport;
        if (typeof viewImport == "string") {
            this.__routeConfig.meta["component"] = () => import(routeInfo.__viewImport);
        } else {
            this.__routeConfig.meta["component"] = viewImport;
        }
        return this;
    }

    this.__children = function (childrenRoute) {
        var children = this.__routeConfig["children"];
        if (children == null) {
            children = []
            this.__routeConfig["children"] = children;
        }
        children.push(childrenRoute);
    }

    this.createChildrenRoute = function () {
        return new RouteInfo(modeManage, this);
    }

    this.route = function () {
        return this.__routeConfig;
    }

    this.build = function () {
        if (this.__modelImport != null) {
            modeManage.__routeInfo(this)
        }
        if (this.mainRoute != null) {
            this.__children(this.__children);
        }
        return this;
    }

    this.icon = function (icon) {
        this.__icon = icon;
        this.__routeConfig.meta["icon"] = icon;
        return this;
    }

}


function once(fn) {
    var called = false;
    return function () {
        var args = [],
            len = arguments.length;
        while (len--) args[len] = arguments[len];

        if (called) {
            return
        }
        called = true;
        return fn.apply(this, args)
    }
}


function ModeManage() {
    var modelCache = {};

    var routeInfoCache = {};

    var childrenRoute = [];

    this.__routeInfo = function (routeInfo) {
        routeInfoCache[routeInfo.path] = routeInfo;
        childrenRoute.push({
            path: routeInfo.path,
            component: () => import(routeInfo.__viewImport),
            name: routeInfo.path,
            meta: {
                title: routeInfo.title,
                noCache: true
            }
        });

        if(modelCache[routeInfo.path] != null){
            return;
        }

        var resolve = once(function (resolvedDef) {
            modelCache[routeInfo.path] = resolvedDef;
            modelCache[routeInfo.__routeConfig.name] = resolvedDef;
            console.log(resolvedDef);
            console.log(routeInfo.path + "  加载成功");
        });

        var reject = once(function (reason) {
            var msg = "Failed to resolve async component " + routeInfo.path;
            console.log(msg);
            console.log(reason);

        });
        import(routeInfo.__modelImport).then(resolve, reject);
    }

    this.loadData = function(name , path){
        if(modelCache[name] != null){
            return;
        }
        var resolve = once(function (resolvedDef) {
            modelCache[name] = resolvedDef;
            console.log(resolvedDef);
            console.log(routeInfo.path + "  加载成功");
        });

        var reject = once(function (reason) {
            var msg = "Failed to resolve async component " + routeInfo.path;
            console.log(msg);
            console.log(reason);

        });
        import(path).then(resolve, reject);
    }

    this.getViewModel = function (vue, name) {
        var viewModel = null;
        if(name != null){
            viewModel = modelCache[name];
        }
        if(viewModel == null){
            viewModel = modelCache[vue.$route.name];
        }
        if(viewModel == null ){
            viewModel = modelCache[vue.view.path];
        }
        
        viewModel = viewModel.default.clone()
        console.log(viewModel);
        viewModel.vue(vue);
        return viewModel;
    }

    this.routeInfo = function () {
        return new RouteInfo(this);
    }

    this.getRoute = function () {
        return childrenRoute;
    }

}


var modeManage = new ModeManage();

//module.exports = modeManage

export default modeManage;
