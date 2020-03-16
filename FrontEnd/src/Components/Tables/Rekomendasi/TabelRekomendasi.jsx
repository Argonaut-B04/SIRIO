import React from 'react';
import BootstrapTable from "react-bootstrap-table-next";
import "./TabelRekomendasi.module.css";
import "react-bootstrap-table-next/dist/react-bootstrap-table2.min.css"
import paginationFactory from 'react-bootstrap-table2-paginator';
import 'react-bootstrap-table2-paginator/dist/react-bootstrap-table2-paginator.min.css';
import ToolkitProvider, { Search } from 'react-bootstrap-table2-toolkit';
import overlayFactory from 'react-bootstrap-table2-overlay';

export default class TabelRekomendasi extends React.Component {

    indication() {
        return "No Data in Table"
    }

    priceFormatter(cell, row) {
        if (cell === "10000") {
            return (
                <span>
                    <strong style={{ color: 'red' }}>$ {cell} NTD(Sales!!)</strong>
                </span>
            );
        }

        return (
            <span>$ {cell} NTD</span>
        );
    }

    rowNumber(cell, row, rowIndex, formatExtraData) {
        return (
            <>{rowIndex + 1}</>
        )
    }

    getButtons(cell) {
        return (
            <div className="d-flex align-items-center justify-content-around">
                <a className="btn-sirio-primary" href="/hasil">Hasil Pemeriksaan</a>
                <a className="btn-sirio-primary" href="https://www.google.com/">Tenggat Waktu</a>
                <a className="btn-sirio-primary" href="https://www.google.com/">Reminder</a>
            </div>
        )
    }

    columns = [{
        isDummyField: true,
        text: 'NO',
        sort: true,
        classes: 'text-center',
        headerStyle: (colum, colIndex) => {
            return { width: "50px", textAlign: 'center' };
        },
        formatter: this.rowNumber

    }, {
        dataField: 'namaRekomendasi',
        text: 'NAMA',
        sort: true,
        headerStyle: (colum, colIndex) => {
            return { width: "25%", textAlign: 'left' };
        }

    }, {
        dataField: 'statusRekomendasi',
        text: 'STATUS',
        sort: true,
        formatter: this.priceFormatter,
        headerStyle: (colum, colIndex) => {
            return { width: "20%", textAlign: 'left' };
        }
    }, {
        dataField: 'id',
        formatter: this.getButtons
    }];

    products = [
        { "id": 10, "namaRekomendasi": "Nathan", "statusRekomendasi": 10000 },
        { "id": 1, "namaRekomendasi": "Nathan1", "statusRekomendasi": 110000 },
        { "id": 1, "namaRekomendasi": "Nathan1", "statusRekomendasi": 110000 },
        { "id": 1, "namaRekomendasi": "Nathan1", "statusRekomendasi": 110000 },
        { "id": 1, "namaRekomendasi": "Nathan1", "statusRekomendasi": 110000 },
        { "id": 1, "namaRekomendasi": "Nathan1", "statusRekomendasi": 110000 },
        { "id": 1, "namaRekomendasi": "Nathan1", "statusRekomendasi": 110000 },
        { "id": 1, "namaRekomendasi": "Nathan1", "statusRekomendasi": 110000 },
        { "id": 1, "namaRekomendasi": "Nathan1", "statusRekomendasi": 110000 },
        { "id": 1, "namaRekomendasi": "Nathan1", "statusRekomendasi": 110000 },
        { "id": 1, "namaRekomendasi": "Nathan1", "statusRekomendasi": 110000 },
        { "id": 1, "namaRekomendasi": "Nathan1", "statusRekomendasi": 110000 },
        { "id": 1, "namaRekomendasi": "Nathan1", "statusRekomendasi": 110000 },
        { "id": 1, "namaRekomendasi": "Nathan1", "statusRekomendasi": 110000 },
        { "id": 2, "namaRekomendasi": "Nathan2", "statusRekomendasi": 12000 },
        { "id": 1, "namaRekomendasi": "Nathan1", "statusRekomendasi": 110000 },
    ]

    defaultSorted = [{
        dataField: 'id',
        order: 'asc'
    }];

    customTotal = (from, to, size) => (
        <span className="react-bootstrap-table-pagination-total text-secondary">
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
            //TODO: bikin jadi komponen baru
            <li className="page-item">
                <a href="/" className="sirio-pagination-button" onClick={handleClick}>{page}</a>
            </li>
        );
    };

    pagination = paginationFactory({
        sizePerPage: 10,
        prePageText: "Previous",
        nextPageText: "Next",
        showTotal: true,
        paginationTotalRenderer: this.customTotal,
        pageButtonRenderer: this.pageButtonRenderer
    })

    overlay = overlayFactory({ spinner: true, styles: { overlay: (base) => ({ ...base, background: 'rgba(255, 0, 0, 0.5)' }) } })

    render() {
        const { indication, products, columns, defaultSorted, pagination, overlay } = this;
        const { SearchBar } = Search;
        return (
            <div>
                <h1>Daftar Rekomendasi</h1>
                <ToolkitProvider
                    bootstrap4
                    keyField='id'
                    data={products}
                    columns={columns}
                    search
                >
                    {
                        props => (
                            <div>
                                <div className="text-right">
                                    <label className="m-2">Search: </label>
                                    <SearchBar
                                        {...props.searchProps}
                                        placeholder
                                    />
                                </div>
                                <BootstrapTable
                                    {...props.baseProps}
                                    ref={n => this.node = n}
                                    striped
                                    hover
                                    condensed
                                    noDataIndication={indication}
                                    defaultSorted={defaultSorted}
                                    pagination={pagination}
                                    overlay={overlay}
                                />
                            </div>
                        )
                    }
                </ToolkitProvider>
            </div>
        );
    }
} 