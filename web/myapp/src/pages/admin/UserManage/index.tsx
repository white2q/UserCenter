import type {ActionType, ProColumns} from '@ant-design/pro-components';
import {ProTable, TableDropdown} from '@ant-design/pro-components';
import {useRef} from 'react';
import {searchUsers} from "@/services/ant-design-pro/api";
import {Image} from "antd";

const columns: ProColumns<API.CurrentUser>[] = [
  {
    dataIndex: 'id',
    valueType: 'indexBorder',
    width: 48,
  },
  {
    title: "账号",
    dataIndex: 'account',
    copyable: true
  },
  {
    title: "用户名",
    dataIndex: 'nickName',
    copyable: true
  },
  {
    title: "头像",
    dataIndex: 'avatar',
    render: (_, record) => (
      <div>
        <Image src={record.avatar} width={50} />
      </div>
    )
  },
  {
    title: "性别",
    dataIndex: 'gender',
  },
  {
    title: "联系方式",
    dataIndex: 'phone',
    copyable: true
  },
  {
    title: "邮件",
    dataIndex: 'email',
    copyable: true
  },
  {
    title: "状态",
    dataIndex: 'status',
    filters: true,
    onFilter: true,
  },
  {
    title: "角色",
    dataIndex: 'userRole',
    valueType: "select",
    valueEnum: {
      0: {text: '普通用户', status: "Default"},
      1: {text: "管理员", status: "Success"},
    },
  },
  {
    title: "创建时间",
    dataIndex: 'createTime',
    valueType: "dateTime",
    copyable: true
  },
  {
    title: '操作',
    valueType: 'option',
    key: 'option',
    render: (text, record, _, action) => [
      <a
        key="editable"
        onClick={() => {
          action?.startEditable?.(record.id);
        }}
      >
        编辑
      </a>,
      <a href={record.url} target="_blank" rel="noopener noreferrer" key="view">
        查看
      </a>,
      <TableDropdown
        key="actionGroup"
        onSelect={() => action?.reload()}
        menus={[
          { key: 'copy', name: '复制' },
          { key: 'delete', name: '删除' },
        ]}
      />,
    ],
  },
];

export default () => {
  const actionRef = useRef<ActionType>();
  return (
    <ProTable<API.CurrentUser>
      columns={columns}
      actionRef={actionRef}
      cardBordered
      request={async (params = {}, sort, filter) => {
        const userList = await searchUsers();
        return {
          data: userList
        }
      }}
      editable={{
        type: 'multiple',
      }}
      columnsState={{
        persistenceKey: 'pro-table-singe-demos',
        persistenceType: 'localStorage',
        onChange(value) {
          console.log('value: ', value);
        },
      }}
      rowKey="id"
      search={{
        labelWidth: 'auto',
      }}
      options={{
        setting: {
          listsHeight: 400,
        },
      }}
      form={{
        // 由于配置了 transform，提交的参与与定义的不同这里需要转化一下
        syncToUrl: (values, type) => {
          if (type === 'get') {
            return {
              ...values,
              created_at: [values.startTime, values.endTime],
            };
          }
          return values;
        },
      }}
      pagination={{
        pageSize: 5,
        onChange: (page) => console.log(page),
      }}
      dateFormatter="string"
      headerTitle="高级表格"
      toolBarRender={() => [

      ]}
    />
  );
};
