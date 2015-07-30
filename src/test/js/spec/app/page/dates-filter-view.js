define([
    'backbone',
    'moment',
    'find/app/page/date/dates-filter-view',
    'find/app/model/dates-filter-model'
], function(Backbone, moment, DatesFilterView, DatesFilterModel) {

    describe('Dates Filter View', function() {
        beforeEach(function() {
            this.now = moment();
            this.twoMonthsAgo = moment().subtract(2, 'months');

            this.queryModel = new Backbone.Model();
            this.datesFilterModel = new Backbone.Model();

            this.datesFilterView = new DatesFilterView({
                queryModel: this.queryModel,
                datesFilterModel: this.datesFilterModel
            });

            this.datesFilterView.render();
        });

        describe('after initialization', function() {

            it('should not tick any date range', function() {
                var checkboxes = this.datesFilterView.$('[data-id] i:not(.checked)');

                var anyTicked = _.find(checkboxes, function(checkbox) {
                    return !$(checkbox).hasClass('hide');
                });

                expect(anyTicked).not.toBeDefined();
            });

        });

        describe('after this.datesFilterModel.setDateRange is called with weeks', function() {
            beforeEach(function() {
                this.datesFilterModel.set({
                    minDate: moment(),
                    maxDate: moment(),
                    dateRange: DatesFilterModel.dateRange.week
                });
            });

            it('should tick the weeks checkbox', function() {
                expect(this.datesFilterView.$("[data-id='" + DatesFilterModel.dateRange.week + "'] i").hasClass('hide')).toBe(false);
            });

            it('should change the dateRange on the queryModel to weeks', function() {
                expect(this.datesFilterModel.get('dateRange')).toBe(DatesFilterModel.dateRange.week);
            });

            describe('after this.datesFilterModel.setDateRange is called with null', function() {
                beforeEach(function() {
                    this.datesFilterModel.set('dateRange', null);
                });

                it('should not tick any date range', function() {
                    var checkboxes = this.datesFilterView.$("[data-id] i");
                    var anyTicked = _.find(checkboxes, function(checkbox) {
                        return !$(checkbox).hasClass('hide');
                    });
                    expect(anyTicked).toBe(undefined);
                });

                it('should set the dateRange attribute on the datesFilterModel to null and the minDate and maxDate attributes should be null', function() {
                    expect(this.datesFilterModel.get('dateRange')).toBe(null);
                });
            });
        });

        describe('after this.datesFilterModel.setDateRange is called with custom', function() {
            beforeEach(function() {
                this.datesFilterModel.set('dateRange', DatesFilterModel.dateRange.custom);
            });

            it('should tick the custom checkbox', function() {
                expect(this.datesFilterView.$("[data-id='" + DatesFilterModel.dateRange.custom + "'] i").hasClass('hide')).toBe(false);
            });

            it('should change the dateRange on the datesFilterModel to custom', function() {
                expect(this.datesFilterModel.get('dateRange')).toBe(DatesFilterModel.dateRange.custom);
            });

            it('should set the minDate and maxDate attributes on the queryModel to null', function() {
                expect(this.queryModel.get('maxDate')).toBeFalsy();
                expect(this.queryModel.get('minDate')).toBeFalsy();
            });

            describe('then this.datesFilterModel.setDateRange is called with month', function() {
                beforeEach(function() {
                    this.datesFilterModel.set('dateRange', DatesFilterModel.dateRange.month);
                });

                it('should tick the months checkbox', function() {
                    expect(this.datesFilterView.$("[data-id='" + DatesFilterModel.dateRange.month + "'] i").hasClass('hide')).toBe(false);
                });

                describe('then this.datesFilterModel.setDateRange is called with custom', function() {
                    beforeEach(function() {
                        this.datesFilterModel.set('dateRange', DatesFilterModel.dateRange.custom);
                    });

                    it('should tick the custom checkbox', function() {
                        expect(this.datesFilterView.$("[data-id='" + DatesFilterModel.dateRange.custom + "'] i").hasClass('hide')).toBe(false);
                    });
                });
            });
        });

        describe('after custom is set on the datesFilterModel', function() {
            beforeEach(function() {
                this.datesFilterModel.set({
                    dateRange: 'custom'
                });
            });

            it('should show the datepickers', function() {
                expect($('.search-dates-wrapper').hasClass('hide')).toBe(false);
            });

            describe('after setting min and max dates on the datesFilterModel the new values should be reflected in the input', function() {
                beforeEach(function() {
                    this.queryModel.set({
                        maxDate: this.now,
                        minDate: this.twoMonthsAgo
                    });
                });

                it('should clear the text from the display box', function() {
                    expect(this.datesFilterView.$('.results-filter-min-date input').val().length > 5).toBe(true);
                    expect(this.datesFilterView.$('.results-filter-max-date input').val().length > 5).toBe(true);
                });
            });
        });
    });
});