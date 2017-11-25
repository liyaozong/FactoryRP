/**
 * Created by Administrator on 2016/5/25.
 */
var consumerToBankApp = angular.module('myApp.consumerToBank', [ 'ui.router','tm.pagination','ngResource'])
    .config(function ($stateProvider, $urlRouterProvider) {
        $stateProvider
            .state("main.QueryToBankConsumerOrderNew", {
                url: "/QueryToBankConsumerOrderNew",
                showName:"查询全部放款订单",
                params: {'needAll': null},
                templateUrl: "views/daianla-consumer-toBank/consumerToBankAllOrder.html",
                controller: 'ToBankAllOrderController'
            })
    })
    .config(function ($stateProvider, $urlRouterProvider) {
        $stateProvider
            .state("main.QueryCommitmentOrder", {
                url: "/QueryCommitmentOrder",
                showName:"承诺函合规",
                params: {'needAll': null},
                templateUrl: "views/daianla-consumer-toBank/consumerCommitmentOrder.html",
                controller: 'commitmentOrderController'
            })
    })
    .config(function ($stateProvider, $urlRouterProvider) {
        $stateProvider
            .state("main.CommitmentOrderOrderDetail", {
                url: "/CommitmentOrderOrderDetail",
                showName:"承诺函详情",
                params: {'userId': null,'id':null,'hideData':null,'hideReset':null,'Need':null},
                templateUrl: "views/daianla-consumer-toBank/commitmentOrderDetail.html",
                controller: 'commitmentOrderDetailController'
            })
    })
    .config(function ($stateProvider, $urlRouterProvider) {
        $stateProvider
            .state("main.QueryNeedSendToBankOrder", {
                url: "/QueryNeedSendToBankOrder",
                showName:"待推送银行订单",
                params: {'needAll': null,'Need':null},
                templateUrl: "views/daianla-consumer-toBank/consumerNeedSendToBankOrderList.html",
                controller: 'needSendToBankOrderListController'
            })
    })
    .config(function ($stateProvider, $urlRouterProvider) {
        $stateProvider
            .state("main.QueryNeedSendToCCBOrder", {
                url: "/QueryNeedSendToCCBOrder",
                showName:"待推送建行订单",
                params: {'needAll': null,'Need':null},
                templateUrl: "views/daianla-consumer-toBank/consumerNeedSendToCCBankOrderList.html",
                controller: 'needSendToCCBankOrderListController'
            })
    })
    .config(function ($stateProvider, $urlRouterProvider) {
        $stateProvider
            .state("main.QueryNeedSendToBankOrderLoan", {
                url: "/QueryNeedSendToBankOrderLoan",
                showName:"待推送银行订单(经营贷)",
                params: {'needAll': null,'Need':null},
                templateUrl: "views/daianla-consumer-toBank/consumerNeedSendToBankLoanOrderList.html",
                controller: 'needSendToBankLoanOrderListController'
            })
    })
    .config(function ($stateProvider, $urlRouterProvider) {
        $stateProvider
            .state("main.QueryHadSendToBankOrder", {
                url: "/QueryHadSendToBankOrder",
                showName:"推送银行成功订单",
                params: {'needAll': null},
                templateUrl: "views/daianla-consumer-toBank/consumerHadSendToBankOrderList.html",
                controller: 'hadSendToBankOrderListController'
            })
    })
    .config(function ($stateProvider, $urlRouterProvider) {
        $stateProvider
            .state("main.QuerySendFailToBankOrder", {
                url: "/QuerySendFailToBankOrder",
                showName:"推送银行失败订单",
                params: {'needAll': null},
                templateUrl: "views/daianla-consumer-toBank/consumerSendFailToBankOrderList.html",
                controller: 'hadFailSendToBankOrderListController'
            })
    })
    .config(function ($stateProvider, $urlRouterProvider) {
        $stateProvider
            .state("main.QueryToBankHadSubmitList", {
                url: "/QueryToBankHadSubmitList",
                showName:"已导入客户列表",
                params: {'needAll': null},
                templateUrl: "views/daianla-consumer-toBank/consumerToBankHadSubmitList.html",
                controller: 'ToBankHadSubmitListController'
            })
    })
    .config(function ($stateProvider, $urlRouterProvider) {
        $stateProvider
            .state("main.consumerToBankOrderDetailSearch", {
                url: "/consumerToBankOrderDetailSearch",
                showName:"订单详情查看",
                params: {'userId': null,'id':null,'hideData':null,'hideReset':null,'Need':null,"isZwbank":null},
                templateUrl: "views/daianla-consumer-toBank/consumerToBankOrderDetailSearch.html",
                controller: 'consumerToBankOrderDetailController'
            })
    })
    .config(function ($stateProvider, $urlRouterProvider) {
        $stateProvider
            .state("main.consumerToBankOrderDetailGiveUp", {
                url: "/consumerToBankOrderDetailGiveUp",
                showName:"订单详情处理",
                params: {'userId': null,'id':null,'hideData':null,'hideReset':null,'Need':null,'giveUp':null},
                templateUrl: "views/daianla-consumer-toBank/consumerToBankLoanOrderDetailGiveUp.html",
                controller: 'consumerToBankOrderDetailController'
            })
    })
    .config(function ($stateProvider, $urlRouterProvider) {
        $stateProvider
            .state("main.consumerToBankOrderDetail", {
                url: "/consumerToBankOrderDetail",
                showName:"订单详情处理(消费贷)",
                params: {'userId': null,'id':null,'hideData':null,'hideReset':null,'Need':null},
                templateUrl: "views/daianla-consumer-toBank/consumerToBankOrderDetail.html",
                controller: 'consumerToBankOrderDetailController'
            })
    })
    .config(function ($stateProvider, $urlRouterProvider) {
        $stateProvider
            .state("main.consumerToBankLoanOrderDetail", {
                url: "/consumerToBankLoanOrderDetail",
                showName:"推送订单详情(经营贷)",
                params: {'userId': null,'id':null,'hideData':null,'hideReset':null,'Need':null},
                templateUrl: "views/daianla-consumer-toBank/consumerToBankLoanOrderDetail.html",
                controller: 'consumerToBankLoanOrderDetailController'
            })
    })
        .config(function ($stateProvider, $urlRouterProvider) {
            $stateProvider
                .state("main.consumerToBankLoanOrderDetailDeal", {
                    url: "/consumerToBankLoanOrderDetailDeal",
                    showName:"建行订单(经营贷)",
                    params: {'userId': null,'id':null,'hideData':null,'hideReset':null,'Need':null},
                    templateUrl: "views/daianla-consumer-toBank/consumerToBankLoanOrderDetailDeal.html",
                    controller: 'consumerToBankLoanOrderDetailController'
                })
        })
        .config(function ($stateProvider, $urlRouterProvider) {
            $stateProvider
                .state("main.consumerToCCBankLoanOrderDetail", {
                    url: "/consumerToCCBankLoanOrderDetail",
                    showName:"推送建行订单详情",
                    params: {'userId': null,'id':null,'hideData':null,'hideReset':null,'Need':null},
                    templateUrl: "views/daianla-consumer-toBank/consumerToCCBankLoanOrderDetail.html",
                    controller: 'consumerToBankLoanOrderDetailController'
                })
        })
        .config(function ($stateProvider, $urlRouterProvider) {
            $stateProvider
                .state("main.consumerToBankLoanOrderDetailSearch", {
                    url: "/consumerToBankLoanOrderDetailSearch",
                    showName:"推送订单详情(经营贷)",
                    params: {'userId': null,'id':null,'hideData':null,'hideReset':null,'Need':null},
                    templateUrl: "views/daianla-consumer-toBank/consumerToBankLoanOrderDetailSearch.html",
                    controller: 'consumerToBankLoanOrderDetailController'
                })
        })
        .config(function ($stateProvider, $urlRouterProvider) {
            $stateProvider
                .state("main.consumerYoungToBankOrderList", {
                    url: "/consumerYoungToBankOrderList",
                    showName:"青春贷订单",
                    params: {'userId': null,'id':null,'hideData':null,'hideReset':null,'Need':null},
                    templateUrl: "views/daianla-consumer-toBank/consumerYoungLoanOrder.html",
                    controller: 'consumerYoungToBankListController'
                })
        })
        .config(function ($stateProvider, $urlRouterProvider) {
            $stateProvider
                .state("main.consumerToBankYoungOrderDetail", {
                    url: "/consumerToBankYoungOrderDetail",
                    showName:"青春贷订单详情",
                    params: {'id': null},
                    templateUrl: "views/daianla-consumer-toBank/consumerToBankYoungOrderDetail.html",
                    controller: 'consumerToBankOrderDetailController'
                })
        })
        .config(function ($stateProvider, $urlRouterProvider) {
            $stateProvider
                .state("main.operatorReportTobank", {
                    params: {'userId': null},
                    url: "/operatorReportTobank",
                    showName:"运营商报告",
                    templateUrl: "views/daianla-consumer-audit/opertatorReport.html",
                    controller: 'operatorReportController'
                })
        })
        .config(function ($stateProvider, $urlRouterProvider) {
            $stateProvider
                .state("main.consumerPublicLoan", {
                    url: "/consumerPublicLoan",
                    showName:"众网小贷订单",
                    templateUrl: "views/daianla-consumer-toBank/consumerPublicLoan.html",
                    controller: 'consumerPublicLoanController'
                })
        })
        .config(function ($stateProvider, $urlRouterProvider) {
            $stateProvider
                .state("main.YouLaFastLoanOrder", {
                    url: "/YouLaFastLoanOrder",
                    showName:"优啦速贷订单",
                    params: {'needAll': null},
                    templateUrl: "views/daianla-consumer-toBank/consumerYouLaFastLoanOrderList.html",
                    controller: 'YouLaFastLoanOrderListController'
                })
        })
    ;
