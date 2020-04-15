import React, { Component } from 'react';
import BootstrapTable from "react-bootstrap-table-next";
import paginationFactory from 'react-bootstrap-table2-paginator';
import ToolkitProvider, { Search } from 'react-bootstrap-table2-toolkit';
import overlayFactory from 'react-bootstrap-table2-overlay';
import classes from './SirioTable.module.css';
import "react-bootstrap-table-next/dist/react-bootstrap-table2.min.css"
import 'react-bootstrap-table2-paginator/dist/react-bootstrap-table2-paginator.min.css';
import SirioComponentHeader from '../Header/SirioComponentHeader';
import ComponentWrapper from '../../Layout/ComponentWrapper';

/**
 * Kelas komponen tabel untuk Sirio secara umum
 * 
 * Props yang tersedia  :
 * - title              : String, Judul tabel
 * - headerButton       : Komponen, Akan ditampilkan di sisi kanan title
 * - id                 : String, parameter json 'id' dari data
 * - data               : JSON, data yang akan ditampilkan berdasarkan definisi columns
 * - columns            : List of JSON, definisi mengenai apa yang harus ditampilkan pada kolom tersebut. Referensi: react-bootstra-table2 columns playground
 * - includeSearchBar   : Boolean, switch untuk menampilkan searchbar atau tidak
 */
export default class SirioTable extends Component {

    constructor(props) {
        super(props);

        this.state = {
            pageNum: 1,
            pageSize: 10
        }

        this.renderRowNumber = this.renderRowNumber.bind(this);
        this.numberFormatter = this.numberFormatter.bind(this);
    }

    // Fungsi untuk menampilkan informasi jumlah entry tabel
    // TODO: Ubah menjadi sesuai dengan desain
    customTotal = (from, to, size) => (
        <span className="text-secondary ml-5">
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
                <p className="sirio-pagination-button" onClick={handleClick}>{page}</p>
            </li>
        );
    };

    sizePerPageRenderer = ({
        options,
        currSizePerPage,
        onSizePerPageChange
    }) => (
            <div className="btn-group" role="group">
                {
                    options.map((option) => {
                        const isSelect = currSizePerPage === `${option.page}`;
                        return (
                            <button
                                key={option.text}
                                type="button"
                                onClick={() => onSizePerPageChange(option.page)}
                                className={`btn ${isSelect ? 'btn-primary' : 'btn-outline-primary'}`}
                            >
                                {option.text}
                            </button>
                        );
                    })
                }
            </div>
        );

    // Definisi kustomisasi Pagination
    pagination =
        paginationFactory({
            sizePerPage: 10,
            prePageText: "Previous",
            nextPageText: "Next",
            showTotal: true,
            hidePageListOnlyOnePage: true,
            paginationTotalRenderer: this.customTotal,
            onPageChange: (page, sizePerPage) => {
                this.renderRowNumber(page, sizePerPage)
            },
            onSizePerPageChange: (sizePerPage, page) => {
                this.renderRowNumber(page, sizePerPage)
            },
            sizePerPageRenderer: this.sizePerPageRenderer
        })

    // Overlay adalah animasi singkat yang ditampilkan pada tabel ketika isi tabel sedang dirender
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

    renderRowNumber(pageNum, pageSize) {
        this.setState({
            pageNum: pageNum,
            pageSize: pageSize
        })
    }

    numberFormatter(enumObject) {
        return (this.state.pageNum - 1) * this.state.pageSize + (enumObject + 1);
    }

    columns = [{
        dataField: 'noData',
        text: 'NO',
        isDummyField: true,
        searchable: false,
        classes: [classes.rowNumber, "d-none d-sm-table-cell"].join(" "),
        headerClasses: [classes.colheader, "d-none d-sm-table-cell"].join(" "),
        headerStyle: () => {
            return { width: "50px", textAlign: 'center' };
        },
        formatter: (cell, row, enumObject, index) => this.numberFormatter(enumObject),
    }]

    // Fungsi render SirioTable
    render() {
        const { columns, props, pagination, overlay } = this;
        const { noHeader, customHeader, headerButton, title, subtitle, betweenTitleSubtitle, id, data, columnsDefinition, includeSearchBar, cellEdit, defaultSorted, indication, footerContent } = props;
        const { SearchBar } = Search;
        
        // eslint-disable-next-line
        var { node } = this;
        const finalColumnsDefinition = columns.concat(columnsDefinition);
        return (
            <div>
                {noHeader || customHeader ? customHeader :
                    <SirioComponentHeader
                        headerButton={headerButton}
                        title={title}
                        subtitle={subtitle}
                        betweenTitleSubtitle={betweenTitleSubtitle}
                    />
                }
                <ComponentWrapper>
                    <ToolkitProvider
                        bootstrap4
                        keyField={id}
                        data={data}
                        columns={finalColumnsDefinition}
                        search={{
                            searchFormatted: true
                        }}
                    >
                        {
                            props => (
                                <div>
                                    {includeSearchBar ?
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
                                        ref={n => node = n}
                                        striped
                                        cellEdit={cellEdit}
                                        hover
                                        defaultSorted={defaultSorted}
                                        noDataIndication={indication || "No Data"}
                                        pagination={pagination}
                                        overlay={overlay}
                                        classes="table-responsive-lg"
                                    />
                                </div>
                            )
                        }
                    </ToolkitProvider>
                    {footerContent &&
                        <div className={classes.footerWrapper}>
                            {footerContent}
                        </div>
                    }
                </ComponentWrapper>
            </div>
        );
    }
};