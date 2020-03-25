import React, { Component } from 'react';
import BootstrapTable from "react-bootstrap-table-next";
import paginationFactory from 'react-bootstrap-table2-paginator';
import ToolkitProvider, { Search } from 'react-bootstrap-table2-toolkit';
import overlayFactory from 'react-bootstrap-table2-overlay';
import classes from './SirioTable.module.css';
import "react-bootstrap-table-next/dist/react-bootstrap-table2.min.css"
import 'react-bootstrap-table2-paginator/dist/react-bootstrap-table2-paginator.min.css';

class SirioTable extends Component {

    indication() {
        return "No Data in Table"
    }

    customTotal = (from, to, size) => (
        <span className="react-bootstrap-table-pagination-total text-secondary ml-5">
            Show {from} to {to} of {size} entries
        </span>
    );

    pageButtonRenderer = ({
        page,
        active,
        disabled,
        title,
        onPageChange
    }) => {
        const handleClick = (e) => {
            e.preventDefault();
            onPageChange(page);
        };
        return (
            <li className="page-item" key={page} >
                <a href="https://www.google.com/" className="sirio-pagination-button" onClick={handleClick}>{page}</a>
            </li>
        );
    };

    pagination = paginationFactory({
        sizePerPage: 10,
        prePageText: "Previous",
        nextPageText: "Next",
        showTotal: true,
        paginationTotalRenderer: this.customTotal,
        // pageButtonRenderer: this.pageButtonRenderer
    })

    overlay = overlayFactory({
        spinner: true,
        styles: {
            overlay: (base) => (
                {
                    ...base,
                    background: 'rgba(255, 0, 0, 0.5)'
                }
            )
        }
    })

    render() {
        const { SearchBar } = Search;
        return (
            <div>
                <div className={classes.headerWrapper}>
                    <h1 className={classes.title}>
                        {this.props.title}
                    </h1>
                    {this.props.headerButton}
                </div>
                <div className={classes.toolkitWrapper}>
                    <ToolkitProvider
                        bootstrap4
                        keyField={this.props.id}
                        data={this.props.data}
                        columns={this.props.columnsDefinition}
                        search
                    >
                        {
                            props => (
                                <div>
                                    {this.props.includeSearchBar ?
                                        <div className="text-right">
                                            <label className="m-2">Search: </label>
                                            <SearchBar
                                                {...props.searchProps}
                                                placeholder=""
                                            />
                                        </div>
                                        :
                                        null
                                    }
                                    <BootstrapTable
                                        {...props.baseProps}
                                        ref={n => this.node = n}
                                        striped
                                        hover
                                        condensed
                                        noDataIndication={this.indication}
                                        defaultSorted={this.defaultSorted}
                                        pagination={this.pagination}
                                        overlay={this.overlay}
                                    />
                                </div>
                            )
                        }
                    </ToolkitProvider>
                </div>
            </div>
        );
    }

};
export default SirioTable;
