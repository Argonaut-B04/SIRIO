import React from 'react';
import BootstrapTable from "react-bootstrap-table-next";
import "react-bootstrap-table-next/dist/react-bootstrap-table2.min.css"
import paginationFactory from 'react-bootstrap-table2-paginator';
import 'react-bootstrap-table2-paginator/dist/react-bootstrap-table2-paginator.min.css';
import ToolkitProvider, { Search } from 'react-bootstrap-table2-toolkit';
import overlayFactory from 'react-bootstrap-table2-overlay';
import TableButton from '../../Button/TableButton';
import classes from './TabelRekomendasi.module.css';

export default class TabelRekomendasi extends React.Component {
    indication() {
        return "No Data in Table"
    }

    statusFormatter(cell) {
        switch (cell) {
            case "Draft":
                return (
                    <span style={{ color: '#7F3F98' }}>{cell}</span>
                );
            case "Menunggu Persetujuan":
                return (
                    <span style={{ color: '#8E8E8E' }}>{cell}</span>
                );
            case "Ditolak":
                return (
                    <span style={{ color: '#FF0000' }}>{cell}</span>
                );
            case "Menunggu Pelaksanaan":
                return (
                    <span style={{ color: '#5DBCD2' }}>{cell}</span>
                );
            case "Menunggu Pengaturan Tenggat Waktu":
                return (
                    <span style={{ color: '#F2994A' }}>{cell}</span>
                );
            case "Sedang Dijalankan":
                return (
                    <span style={{ color: '#6FCF97' }}>{cell}</span>
                );
            default:
                return (cell);
        }
    }

    rowNumber(cell, row, rowIndex, formatExtraData) {
        return (
            <>{rowIndex + 1}</>
        )
    }

    getButtons(cell, row) {
        const status = row.statusRekomendasi;
        const recommended = status === "Menunggu Pengaturan Tenggat Waktu";
        const reminderEnable = status === "Menunggu Pelaksanaan" || status === "Sedang Dijalankan";
        return (
            <div className="d-flex align-items-center justify-content-around">
                <TableButton link="http://google.com">
                    Hasil Pemeriksaan
                </TableButton>

                <TableButton link="http://google.com" disabled={!recommended} recommended={recommended}>
                    Tenggat Waktu
                </TableButton>
                <TableButton link="http://google.com" disabled={!reminderEnable}>
                    Reminder
                </TableButton>
            </div>
        )
    }

    columns = [{
        dataField: '',
        isDummyField: true,
        text: 'NO',
        sort: true,
        classes: classes.rowNumber,
        headerClasses: classes.colheader,
        headerStyle: (colum, colIndex) => {
            return { width: "50px", textAlign: 'center' };
        },
        formatter: this.rowNumber

    }, {
        dataField: 'namaRekomendasi',
        text: 'NAMA',
        sort: true,
        classes: classes.rowItem,
        headerClasses: classes.colheader,
        headerStyle: (colum, colIndex) => {
            return { width: "25%", textAlign: 'left' };
        }

    }, {
        dataField: 'statusRekomendasi',
        text: 'STATUS',
        sort: true,
        classes: classes.rowItem,
        formatter: this.statusFormatter,
        headerClasses: classes.colheader,
        headerStyle: (colum, colIndex) => {
            return { width: "20%", textAlign: 'left' };
        }
    }, {
        dataField: 'id',
        text: '',
        headerClasses: classes.colheader,
        classes: classes.rowItem,
        formatter: this.getButtons
    }];

    products = [
        { "id": 10, "namaRekomendasi": "Nathan", "statusRekomendasi": "Menunggu Pengaturan Tenggat Waktu" },
        { "id": 1, "namaRekomendasi": "Nathan1", "statusRekomendasi": "Ditolak" },
        { "id": 2, "namaRekomendasi": "Nathan1", "statusRekomendasi": "Sedang Dijalankan" },
        { "id": 3, "namaRekomendasi": "Nathan1", "statusRekomendasi": "Menunggu Persetujuan" },
        { "id": 4, "namaRekomendasi": "Nathan1", "statusRekomendasi": "Menunggu Pelaksanaan" },
        { "id": 5, "namaRekomendasi": "Nathan1", "statusRekomendasi": "Sedang Dijalankan" },
        { "id": 6, "namaRekomendasi": "Nathan1", "statusRekomendasi": "Draft" },
        { "id": 7, "namaRekomendasi": "Nathan1", "statusRekomendasi": "Draft" },
        { "id": 8, "namaRekomendasi": "Nathan1", "statusRekomendasi": "Draft" },
        { "id": 9, "namaRekomendasi": "Nathan1", "statusRekomendasi": "Draft" },
        { "id": 11, "namaRekomendasi": "Nathan1", "statusRekomendasi": "Draft" },
        { "id": 12, "namaRekomendasi": "Nathan1", "statusRekomendasi": "Draft" },
        { "id": 13, "namaRekomendasi": "Nathan1", "statusRekomendasi": "Draft" },
        { "id": 14, "namaRekomendasi": "Nathan1", "statusRekomendasi": "Draft" },
        { "id": 25, "namaRekomendasi": "Nathan2", "statusRekomendasi": "Draft" },
        { "id": 16, "namaRekomendasi": "Nathan1", "statusRekomendasi": "Draft" },
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
        pageButtonRenderer: this.pageButtonRenderer
    })

    overlay = overlayFactory({ spinner: true, styles: { overlay: (base) => ({ ...base, background: 'rgba(255, 0, 0, 0.5)' }) } })

    render() {
        const { indication, products, columns, defaultSorted, pagination, overlay } = this;
        const { SearchBar } = Search;
        return (
            <div>
                <h1 className={classes.title}>Daftar Rekomendasi</h1>
                <div className={classes.toolkitWrapper}>
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
                                            placeholder=""
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
            </div>
        );
    }
} 